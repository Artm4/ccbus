package ccbus.tool.translator.java2reactserverjava;

import ccbus.tool.intermediate.Node;
import ccbus.tool.intermediate.SymTab;
import ccbus.tool.intermediate.SymTabEntry;
import ccbus.tool.intermediate.SymTabStack;
import ccbus.tool.intermediate.symtabimpl.SymTabKeyImpl;
import ccbus.tool.parser.java.*;
import ccbus.tool.translator.RecursiveHostTranslator;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.util.java.Tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class ClassOrInterfaceBodyService extends GenericTreeTranslator implements TreeTranslator ,RecursiveHostTranslator
{
    Tool tool;
    ASTMethodDeclaration computeMethod=null;


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

    public Node translate(Node nodeBody, TranslatedTree tree) {
        Node node=nodeBody.findFirstUpById(JavaParserTreeConstants.JJTCLASSORINTERFACEDECLARATION);
        tool=((Tool)tree.tool());
        InternalState internalState=new InternalState();

        internalState.symTabStack=tool.symTabStack();
        internalState.tree=tree;
        initClient(internalState);

        ASTClassOrInterfaceDeclaration classOrInterfaceDeclarationResult=
                new ASTClassOrInterfaceDeclaration(JavaParserTreeConstants.JJTCLASSORINTERFACEDECLARATION);
        tree.add(classOrInterfaceDeclarationResult);

        ASTClassOrInterfaceBody classOrInterfaceBody=(ASTClassOrInterfaceBody)
                node.findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACEBODY,1);

        ASTExtendsList extendsList=(ASTExtendsList)
                node.findFirstDownById(JavaParserTreeConstants.JJTEXTENDSLIST,2);

        if(null!=extendsList)
        {
            ASTIdentifier typeIdentifier=(ASTIdentifier)
                    extendsList.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,2);
            if(0!=typeIdentifier.jjtGetFirstToken().image.compareTo("WorkerService"))
            {
                return null;
            }
            typeIdentifier.jjtGetFirstToken().image="WorkerService";
            typeIdentifier.jjtGetLastToken().image="WorkerService";
        }
        else
        {
            return null;
        }


        translateWorker(node,internalState);

        if(internalState.classOrInterfaceBody.jjtGetNumChildren()>0)
        {
            translateClient(classOrInterfaceBody,internalState);
        }


        return null;
    }

    public void translateClient(Node classBody,InternalState internalState)
    {
        if(null==computeMethod)
        {
            return;
        }
        //Node node=computeMethod;

        //node.resetChildren();
        //node.setId(JavaParserTreeConstants.JJTEXPRESSION);

        ASTArguments arguments=new ASTArguments(JavaParserTreeConstants.JJTARGUMENTS);
        ASTArgumentList argumentList=new ASTArgumentList(JavaParserTreeConstants.JJTARGUMENTLIST);
        arguments.add(tool.createNodeLParen());
        arguments.add(argumentList);

        ASTExpression argumentExpression=new ASTExpression(JavaParserTreeConstants.JJTEXPRESSION);
        argumentExpression.add(internalState.clientWorkerPayload);

        argumentList.add(argumentExpression);
        arguments.add(tool.createNodeRParen());


        ASTPrimaryExpression primaryExpression=tool.createPrimaryExpression(
                tool.createPrimaryPrefix(tool.createName("WorkerServer")),
                tool.createPrimarySuffix(tool.createNodeDot(),tool.createIdentifier("computeService")),
                tool.createPrimarySuffix(arguments)
        );

        ASTClassOrInterfaceBodyDeclaration bodyDeclaration=
                new ASTClassOrInterfaceBodyDeclaration(JavaParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION);
        ASTMethodDeclaration methodDeclaration=tool.createMethodDeclaration("compute");
        // should create sym tab and set it for methodDeclaration node as sym tab
        // Code from ecma translator , node is MethodDeclaration
        // ((Tool)tree.tool()).symTabStack().push(node.symTab());
        SymTab methodSymTab=tool.symTabStack().push("compute");
        tool.symTabStack().pop();
        methodDeclaration.symTab(methodSymTab);
        ASTBlock block=new ASTBlock(JavaParserTreeConstants.JJTBLOCK);
        ASTBlockStatement blockStatement=new ASTBlockStatement(JavaParserTreeConstants.JJTBLOCKSTATEMENT);
        ASTStatement statement=new ASTStatement(JavaParserTreeConstants.JJTSTATEMENT);

        ASTMethodDeclarator methodDeclarator=(ASTMethodDeclarator)
                methodDeclaration.findFirstDownById(JavaParserTreeConstants.JJTMETHODDECLARATOR,1);

        block.add(blockStatement);
        blockStatement.add(statement);
        statement.add(primaryExpression);

        bodyDeclaration.add(methodDeclaration);
        methodDeclaration.add(block);
        methodDeclaration.add(methodDeclarator);
        classBody.add(bodyDeclaration);
    }

    public Node translateWorker(Node node,ClassOrInterfaceBodyService.InternalState internalState)
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
                    computeMethod=methodDeclaration;
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
                                                    String className,ASTTypeArguments typeArguments,ClassOrInterfaceBodyService.InternalState internalState)
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
        //typeArguments.detach();
        classOrInterfaceType.add(typeArguments);

        // create package

        ASTPackageDeclaration packageDeclaration=tool.createPackageDeclarationWorkerService(packageSuffix.toArray(new String[0]));
        serverCompilationUnit.add(packageDeclaration);

        ASTImportDeclaration importDeclaration=tool.createImport(
                tool.createName("ccbus","connect","core","ccbus","WorkerServerImpl")
        );

        // No need to add WorkerService package, already done implicitly
        serverCompilationUnit.add(importDeclaration);

        ASTImportDeclaration importDeclarationWorker=tool.createImport(
                tool.createName("ccbus","connect","core","ccbus","WorkerServer")
        );

        // No need to add WorkerService package, already done implicitly
        compilationUnit.add(importDeclarationWorker);

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
                                             ASTClassOrInterfaceBody classOrInterfaceBodyResult,ClassOrInterfaceBodyService.InternalState internalState

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
                            block.findNextDownById(JavaParserTreeConstants.JJTPRIMARYEXPRESSION, 160);

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
                                                        ASTClassOrInterfaceBody classOrInterfaceBodyResult,ClassOrInterfaceBodyService.InternalState internalState)
    {
        if(null==primaryExpression)
        {
            return;
        }
        ASTPrimaryPrefix primaryPrefix =
                (ASTPrimaryPrefix)
                        primaryExpression.findFirstDownById(JavaParserTreeConstants.JJTPRIMARYPREFIX, 1);

        ASTPrimarySuffix primarySuffix =
                (ASTPrimarySuffix)
                        primaryExpression.findNextDownById(JavaParserTreeConstants.JJTPRIMARYSUFFIX, 1);

        translateWorkerServerFieldsPrefix(node,
                classOrInterfaceBodyDeclaration,
                classOrInterfaceBodyResult,
                primaryPrefix,
                primarySuffix,
                internalState
        );

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
                                                   ASTPrimaryPrefix primaryPrefix,ASTPrimarySuffix primarySuffix,ClassOrInterfaceBodyService.InternalState internalState

    )
    {
        ASTName name=
                (ASTName)
                        primaryPrefix.findFirstDownById(JavaParserTreeConstants.JJTNAME,1);

        if(null==name&&null==primarySuffix)
        {
            return;
        }

        ASTIdentifier identifier=null;
        if(null!=name)
        {
            identifier = (ASTIdentifier)
                    name.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER, 1);
        }

        if(null!=primarySuffix)
        {
            identifier=
                    (ASTIdentifier)
                            primarySuffix.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,1);
        }

        if(null==identifier)
        {
            return;
        }


        SymTabEntry entry=internalState.symTabStack.lookup(identifier.image());
        if(null!=entry&&entry.getSymTab().isClass()&&!entry.hasAttribute(SymTabKeyImpl.METHOD))
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

    public void translateClientData(ASTPrimaryExpression primaryExpression,ASTType type,String name,ClassOrInterfaceBodyService.InternalState internalState)
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



    public Node translateWorkerClient(ASTMethodDeclaration methodDeclaration,ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclaration,ClassOrInterfaceBodyService.InternalState internalState)
    {
        internalState.tree.translateRecursive(methodDeclaration,this);
        internalState.classOrInterfaceBody.add(classOrInterfaceBodyDeclaration);
        return null;
    }

    private Node translateDataPayload(ASTClassOrInterfaceBody classOrInterfaceBody,ClassOrInterfaceBodyService.InternalState internalState)
    {
        internalState.classOrInterfaceBody.add(tool.createLiteral("data",internalState.clientData));

        ASTVariableInitializer bindInit=tool.createVariableInitializer(
                tool.createPrimaryPrefix(tool.createNodeThis())
        );
        ASTClassOrInterfaceBodyDeclaration bindDeclaration=tool.createFieldDeclaration(
                "bind",tool.createReferenceType("Object"),bindInit);

        internalState.classOrInterfaceBody.add(bindDeclaration);


        ASTVariableInitializer argsInit=tool.createVariableInitializer(
                tool.createPrimaryPrefix(tool.createNodeIdentifier("arguments"))
        );
        ASTClassOrInterfaceBodyDeclaration argsDeclaration=tool.createFieldDeclaration(
                "args",tool.createReferenceType("Object"),argsInit);

        internalState.classOrInterfaceBody.add(argsDeclaration);
        return null;
    }

    private void initEndpoint(String className,ClassOrInterfaceBodyService.InternalState internalState)
    {
        ASTVariableInitializer endpointInit=tool.createVariableInitializer(
                tool.createPrimaryPrefix(tool.createNodeLiteral(className))
        );
        ASTClassOrInterfaceBodyDeclaration endpoint=tool.createFieldDeclaration(
                "endpoint",tool.createReferenceType("String"),endpointInit);

        internalState.classOrInterfaceBody.add(endpoint);
    }

    private void initResponseType(ASTTypeArguments typeArguments,ClassOrInterfaceBodyService.InternalState internalState)
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
