package ccbus.tool.translator.java2splitjava;

import ccbus.tool.intermediate.Node;
import ccbus.tool.intermediate.SymTabEntry;
import ccbus.tool.intermediate.SymTabStack;
import ccbus.tool.intermediate.symtabimpl.SymTabKeyImpl;
import ccbus.tool.parser.java.*;
import ccbus.tool.translator.RecursiveHostTranslator;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.java2reactserverjava.PrimaryExpression;
import ccbus.tool.util.java.Tool;

import java.util.ArrayList;
import java.util.HashMap;


public class ClassDeclarationForReact extends GenericTreeTranslator implements TreeTranslator ,RecursiveHostTranslator
{
    Tool tool;


    private class InternalState
    {
        public HashMap<String,Boolean> dataTranslated=new HashMap<>();
        public ASTVariableInitializer clientData;
        public ASTVariableInitializer clientWorkerPayload;
        public TranslatedTree tree;
        public SymTabStack symTabStack;
        ASTClassOrInterfaceBody classOrInterfaceBody;
    }

    public void initClient(InternalState internalState)
    {
        internalState.clientData=tool.createNamelessClassExpression("Object");
        internalState.clientWorkerPayload=tool.createNamelessClassExpression("Object");
        internalState.classOrInterfaceBody=(ASTClassOrInterfaceBody)
                internalState.clientWorkerPayload
                        .findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACEBODY,5);
    }

    public Node translate(Node node, TranslatedTree tree) {
        tool=((Tool)tree.tool());
        InternalState internalState=new InternalState();

        internalState.symTabStack=tool.symTabStack();
        internalState.tree=tree;
        initClient(internalState);

        ASTClassOrInterfaceDeclaration classOrInterfaceDeclarationResult=
                new ASTClassOrInterfaceDeclaration(JavaParserTreeConstants.JJTCLASSORINTERFACEDECLARATION);
        tree.add(classOrInterfaceDeclarationResult);

//        ASTIdentifier nodeIdResult= (ASTIdentifier)node.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,2);
//
//        ASTClassOrInterfaceBody classBodyResult=(ASTClassOrInterfaceBody)node.findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACEBODY,2);
//
//
//        ccbus.tool.parser.java.ASTTypeIdentifier typeIdentifier=(ccbus.tool.parser.java.ASTTypeIdentifier)
//                node.findFirstDownById(JavaParserTreeConstants.JJTTYPEIDENTIFIER,2);
//
//        String className=typeIdentifier.image();
//
//        ASTTypeArguments typeArguments=(ASTTypeArguments)
//                node.findFirstDownById(JavaParserTreeConstants.JJTTYPEARGUMENTS,2);
//
//        ASTTypeIdentifier typeIdentifierResult=new ASTTypeIdentifier(EcmaParserTreeConstants.JJTTYPEIDENTIFIER);
//        typeIdentifierResult.jjtSetFirstToken(typeIdentifier.jjtGetFirstToken());
//
//        ccbus.tool.parser.java.ASTExtendsList extendsList=(ccbus.tool.parser.java.ASTExtendsList)
//                node.findFirstDownById(JavaParserTreeConstants.JJTEXTENDSLIST,2);
//
//        classOrInterfaceDeclarationResult.add(typeIdentifierResult);
//        classOrInterfaceDeclarationResult.add(nodeIdResult);
//        classOrInterfaceDeclarationResult.add(classBodyResult);
//
//        if(null!=extendsList)
//        {
//            SimpleNode extendsListResult = (SimpleNode) tree.translateRecursive(extendsList, this);
//            classOrInterfaceDeclarationResult.add(extendsListResult);
//        }
//
//        ccbus.tool.parser.java.ASTTypeParameters typeParameters=
//                (ccbus.tool.parser.java.ASTTypeParameters )
//                        node.findFirstDownById(JavaParserTreeConstants.JJTTYPEPARAMETERS,2);
//        if(typeParameters!=null) {
//            ASTTypeParameters typeParametersResult = (ASTTypeParameters) tree.translate(JavaParserTreeConstants.JJTTYPEPARAMETERS,
//                    typeParameters
//            );
//            classOrInterfaceDeclarationResult.add(typeParametersResult);
//        }

        //this.translateWorkerServer(node,(ASTClassOrInterfaceBodyDeclaration)node,className,typeArguments,internalState);
        translateWorker(node,internalState);
        return null;
    }

    public Node translateWorker(Node node,ClassDeclarationForReact.InternalState internalState)
    {


        ASTClassOrInterfaceBody classOrInterfaceBody=(ASTClassOrInterfaceBody)
                node.findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACEBODY,1);

        ASTExtendsList extendsList=(ASTExtendsList)
                node.findFirstDownById(JavaParserTreeConstants.JJTEXTENDSLIST,1);

        ASTTypeArguments typeArguments=(ASTTypeArguments)
                extendsList.findFirstDownById(JavaParserTreeConstants.JJTTYPEARGUMENTS,2);


        ASTIdentifier identifier=(ASTIdentifier)
                node.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,1);
        String name=identifier.image();

        for(int i=classOrInterfaceBody.jjtGetNumChildren()-1;i>=0;i--)
        {
            if(JavaParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION!=classOrInterfaceBody.jjtGetChild(i).getId())
            {
                continue;
            }
            ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclaration=(ASTClassOrInterfaceBodyDeclaration)classOrInterfaceBody.jjtGetChild(i);
            ASTMethodDeclaration methodDeclaration=(ASTMethodDeclaration)
                    classOrInterfaceBodyDeclaration.findFirstDownById(JavaParserTreeConstants.JJTMETHODDECLARATION,1);
            if(null!=methodDeclaration)
            {
                ((Tool)internalState.tree.tool()).symTabStack().push(methodDeclaration.symTab());
                ASTIdentifier identifierMethod=(ASTIdentifier)
                        methodDeclaration.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,2);
                if(identifierMethod.image().equals("compute"))
                {
                    translateWorkerServer(node,classOrInterfaceBodyDeclaration,name,typeArguments,internalState);
                }
                else
                {
                    // Should throw error
                    //tool.errorTranslate(identifierMethod,"WorkerServer method not declared");
                }
                ((Tool)internalState.tree.tool()).symTabStack().pop();
            }
        }
        classOrInterfaceBody.resetNextSearch();
        translateDataPayload(classOrInterfaceBody,internalState);
        return null;
    }

    public ASTTypeDeclaration translateWorkerServer(Node node,ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclaration,
                                                    String className,ASTTypeArguments typeArguments,ClassDeclarationForReact.InternalState internalState)
    {


        String classParentName=className;


        // fetch package prefix
        ASTCompilationUnit compilationUnit=
                (ASTCompilationUnit)
                        node.findFirstUpById(JavaParserTreeConstants.JJTCOMPILATIONUNIT);
        ArrayList<String> packageSuffix=tool.parseCompilationUnitRelativePathClient(compilationUnit);

        // create new Worker Server class for java
        String serverWorkerClassName=classParentName;
        String serverEndPoint=serverWorkerClassName;

        // remove components package
        packageSuffix.remove(0);
        // build with prefix
        if(packageSuffix.size()>0)
        {
            serverEndPoint=String.join(".",packageSuffix)+"."+serverWorkerClassName;
        }
        initEndpoint(serverEndPoint,internalState);
        initResponseType(typeArguments,internalState);

        Node serverCompilationUnit=tool.getCompilationUnitNodePair().createServerCompilationUnit();
        ASTTypeDeclaration typeDeclaration=tool.createClass(serverWorkerClassName,"WorkerServerImpl");
        ASTClassOrInterfaceType classOrInterfaceType=
                (ASTClassOrInterfaceType)
                        typeDeclaration.findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACETYPE,4);
        typeArguments.detach();
        classOrInterfaceType.add(typeArguments);

        // create package

        ASTPackageDeclaration packageDeclaration=tool.createPackageDeclarationWorkerService(packageSuffix.toArray(new String[0]));
        serverCompilationUnit.add(packageDeclaration);

        ASTImportDeclaration importDeclaration=tool.createImport(
                tool.createName("ccbus","connect","core","ccbus","WorkerServerImpl")
        );

        serverCompilationUnit.add(importDeclaration);

        // add class type
        serverCompilationUnit.add(typeDeclaration);

        ASTClassOrInterfaceBody classOrInterfaceBody=
                (ASTClassOrInterfaceBody)
                        typeDeclaration.findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACEBODY,2);

        translateWorkerServerFields(node,classOrInterfaceBodyDeclaration,classOrInterfaceBody,internalState);

        classOrInterfaceBodyDeclaration.detach();
        classOrInterfaceBody.add(classOrInterfaceBodyDeclaration);

        return typeDeclaration;
    }

    private void translateWorkerServerFields(Node node,
                                             ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclaration,
                                             ASTClassOrInterfaceBody classOrInterfaceBodyResult,ClassDeclarationForReact.InternalState internalState

    )
    {

        ASTMethodDeclaration methodDeclaration=(ASTMethodDeclaration)
                classOrInterfaceBodyDeclaration.findFirstDownById(JavaParserTreeConstants.JJTMETHODDECLARATION,2);

        ASTBlock block=(ASTBlock)
                methodDeclaration.findFirstDownById(JavaParserTreeConstants.JJTBLOCK,1);
        if(null==block)
        {
            return;
        }
        ASTPrimaryExpression primaryExpression;
        do
        {
            primaryExpression =
                    (ASTPrimaryExpression)
                            block.findNextDownById(JavaParserTreeConstants.JJTPRIMARYEXPRESSION, 60);

            translateWorkerServerPrimaryExpression(primaryExpression,node,classOrInterfaceBodyDeclaration,classOrInterfaceBodyResult,internalState);
        }
        while(null!=primaryExpression);
        if(null!=primaryExpression)
        {
            primaryExpression.resetNextSearch();
        }
    }

    private void translateWorkerServerPrimaryExpression(ASTPrimaryExpression primaryExpression,Node node,
                                                        ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclaration,
                                                        ASTClassOrInterfaceBody classOrInterfaceBodyResult,ClassDeclarationForReact.InternalState internalState)
    {
        if(null==primaryExpression)
        {
            return;
        }
        ASTPrimaryPrefix primaryPrefix =
                (ASTPrimaryPrefix)
                        primaryExpression.findFirstDownById(JavaParserTreeConstants.JJTPRIMARYPREFIX, 1);

        translateWorkerServerFieldsPrefix(node,
                classOrInterfaceBodyDeclaration,
                classOrInterfaceBodyResult,
                primaryPrefix,
                internalState
        );

        ASTPrimarySuffix primarySuffix =
                (ASTPrimarySuffix)
                        primaryExpression.findNextDownById(JavaParserTreeConstants.JJTPRIMARYSUFFIX, 1);

        if(null!=primarySuffix)
        {
            ASTPrimaryExpression primaryExpressionInSuffix =
                    (ASTPrimaryExpression)
                            primarySuffix.findNextDownById(JavaParserTreeConstants.JJTPRIMARYEXPRESSION, 30);
            translateWorkerServerPrimaryExpression(primaryExpressionInSuffix,node,classOrInterfaceBodyDeclaration,classOrInterfaceBodyResult,internalState);
        }
    }

    private void translateWorkerServerFieldsPrefix(Node node,
                                                   ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclaration,
                                                   ASTClassOrInterfaceBody classOrInterfaceBodyResult,
                                                   ASTPrimaryPrefix primaryPrefix,ClassDeclarationForReact.InternalState internalState

    )
    {
        ASTName name=
                (ASTName)
                        primaryPrefix.findFirstDownById(JavaParserTreeConstants.JJTNAME,1);
        if(null==name)
        {
            return;
        }
        ASTIdentifier identifier=
                (ASTIdentifier)
                        name.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,1);
        SymTabEntry entry=internalState.symTabStack.lookup(identifier.image());
        if(null!=entry&&entry.getSymTab().isClass())
        {
            if(internalState.dataTranslated.containsKey(entry.getName()))
            {
                return;
            }
            internalState.dataTranslated.put(entry.getName(),true);
            ASTType type = (ASTType) entry.getAttribute(SymTabKeyImpl.PARSED_VAR_TYPE);
            Node declaratorId = (Node) entry.getAttribute(SymTabKeyImpl.PARSED_VAR_DECLARATOR_ID);
            ASTFieldDeclaration fieldDeclaration=new ASTFieldDeclaration(JavaParserTreeConstants.JJTFIELDDECLARATION);
            fieldDeclaration.add(type);
            fieldDeclaration.add(declaratorId);

            ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclarationResult=
                    new ASTClassOrInterfaceBodyDeclaration(JavaParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION);

            ASTModifiers modifiers=new ASTModifiers(JavaParserTreeConstants.JJTMODIFIERS);
            ASTModifier modifier=new ASTModifier(JavaParserTreeConstants.JJTMODIFIER);

            tool.createToken(JavaParserConstants.PUBLIC,"public",modifier);
            modifiers.add(modifier);

            classOrInterfaceBodyDeclarationResult.add(modifiers);
            classOrInterfaceBodyDeclarationResult.add(fieldDeclaration);
            classOrInterfaceBodyResult.add(classOrInterfaceBodyDeclarationResult);

            // create payload here
            ASTPrimaryExpression primaryExpression =
                    (ASTPrimaryExpression)
                            primaryPrefix.findFirstUpById(JavaParserTreeConstants.JJTPRIMARYEXPRESSION, 30);
            translateClientData(primaryExpression,type,entry.getName(),internalState);
        }
    }

    public void translateClientData(ASTPrimaryExpression primaryExpression,ASTType type,String name,ClassDeclarationForReact.InternalState internalState)
    {
        ASTIdentifier identifier=(ASTIdentifier)
                primaryExpression.findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER,3);

        ASTPrimaryExpression primaryExpressionClient=
                new ASTPrimaryExpression(JavaParserTreeConstants.JJTPRIMARYEXPRESSION);

        ASTPrimaryPrefix primaryPrefix=
                new ASTPrimaryPrefix(JavaParserTreeConstants.JJTPRIMARYPREFIX);

        primaryExpressionClient.add(primaryPrefix);

        ASTName nameNode=new ASTName(JavaParserTreeConstants.JJTNAME);
        nameNode.add(identifier);

        primaryPrefix.add(nameNode);

        new PrimaryExpression().translate(primaryExpressionClient,internalState.tree);

        ASTClassOrInterfaceBody classOrInterfaceBody=
                (ASTClassOrInterfaceBody )
                        internalState.clientData.findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACEBODY,5);

        ASTVariableInitializer variableInitializer=tool.createVariableInitializer(primaryExpressionClient);
        ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclaration=tool.createFieldDeclaration(
                name,type,variableInitializer
        );
        classOrInterfaceBody.add(classOrInterfaceBodyDeclaration);
    }



    public Node translateWorkerClient(ASTMethodDeclaration methodDeclaration,ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclaration,ClassDeclarationForReact.InternalState internalState)
    {
        internalState.tree.translateRecursive(methodDeclaration,this);
        internalState.classOrInterfaceBody.add(classOrInterfaceBodyDeclaration);
        return null;
    }

    private Node translateDataPayload(ASTClassOrInterfaceBody classOrInterfaceBody,ClassDeclarationForReact.InternalState internalState)
    {
        internalState.classOrInterfaceBody.add(tool.createLiteral("data",internalState.clientData));

        ASTVariableInitializer bindInit=tool.createVariableInitializer(
                tool.createPrimaryPrefix(tool.createNodeThis())
        );
        ASTClassOrInterfaceBodyDeclaration bindDeclaration=tool.createFieldDeclaration(
                "bind",tool.createReferenceType("Object"),bindInit);

        internalState.classOrInterfaceBody.add(bindDeclaration);
        return null;
    }

    private void initEndpoint(String className,ClassDeclarationForReact.InternalState internalState)
    {
        ASTVariableInitializer endpointInit=tool.createVariableInitializer(
                tool.createPrimaryPrefix(tool.createNodeLiteral(className))
        );
        ASTClassOrInterfaceBodyDeclaration endpoint=tool.createFieldDeclaration(
                "endpoint",tool.createReferenceType("String"),endpointInit);

        internalState.classOrInterfaceBody.add(endpoint);
    }

    private void initResponseType(ASTTypeArguments typeArguments,ClassDeclarationForReact.InternalState internalState)
    {
        ASTIdentifier identifier=(ASTIdentifier)typeArguments.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,5);
        String completeType=identifier.image();
        if(!completeType.equalsIgnoreCase("FileDownload"))
        {
            return;
        }
        // File Download should be http blob response type. By default json
        String responseType="blob";
        ASTVariableInitializer typeInit=tool.createVariableInitializer(
                tool.createPrimaryPrefix(tool.createNodeLiteral(responseType))
        );
        ASTClassOrInterfaceBodyDeclaration type=tool.createFieldDeclaration(
                "responseType",tool.createReferenceType("String"),typeInit);

        internalState.classOrInterfaceBody.add(type);
    }

}
