package ccbus.tool.translator.java2splitjava;

import ccbus.tool.intermediate.Node;
import ccbus.tool.intermediate.SymTabEntry;
import ccbus.tool.intermediate.SymTabStack;
import ccbus.tool.intermediate.symtabimpl.SymTabKeyImpl;
import ccbus.tool.parser.java.*;
import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.RecursiveHostTranslator;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.util.java.TokenList;
import ccbus.tool.util.java.Tool;

import java.util.ArrayList;
import java.util.HashMap;


public class ClassOrInterfaceBody extends GenericTreeTranslator implements TreeTranslator ,RecursiveHostTranslator
{
    Tool tool;

    String tab = "    ";
    String dTab = tab + tab;
    String tTab = tab + dTab;

    private class InternalState
    {
        public HashMap<String, Boolean> dataTranslated = new HashMap<>();
        public ASTVariableInitializer clientData;
        public ASTVariableInitializer clientWorkerPayload;
        public TranslatedTree tree;
        public SymTabStack symTabStack;
        ASTClassOrInterfaceBody classOrInterfaceBody;

        public ASTMethodDeclaration computeDeclaration;
        public String className = "";
        public String argumantName = "";
        public TokenList data;
        public TokenList endPoint;
        public TokenList responseType = null;
        public TokenList argumentType;
    }

    public void initClient(InternalState internalState)
    {
        internalState.clientData = tool.createNamelessClassExpression("Object");
        internalState.clientWorkerPayload = tool.createNamelessClassExpression("Object");
        internalState.classOrInterfaceBody = (ASTClassOrInterfaceBody)
                internalState.clientWorkerPayload
                        .findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACEBODY, 5);
        internalState.computeDeclaration =
                new ASTMethodDeclaration(JavaParserTreeConstants.JJTMETHODDECLARATION);
    }

    public Node translate(Node node, TranslatedTree tree)
    {
        tool = ((Tool) tree.tool());
        InternalState internalState = new InternalState();

        internalState.symTabStack = tool.symTabStack();
        internalState.tree = tree;
        initClient(internalState);

        ASTClassOrInterfaceDeclaration classOrInterfaceDeclaration = (ASTClassOrInterfaceDeclaration) node.findFirstUpById(JavaParserTreeConstants.JJTCLASSORINTERFACEDECLARATION);

        ccbus.tool.parser.java.ASTExtendsList extendsList=(ccbus.tool.parser.java.ASTExtendsList)
                classOrInterfaceDeclaration.findFirstDownById(JavaParserTreeConstants.JJTEXTENDSLIST,2);

        if(null!=extendsList)
        {
                    ccbus.tool.parser.java.ASTIdentifier typeIdentifier=(ccbus.tool.parser.java.ASTIdentifier)
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

        translateWorker(classOrInterfaceDeclaration, internalState);

        if (internalState.computeDeclaration!=null)
        {

            buildCompute(internalState);
            ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclaration =
                    new ASTClassOrInterfaceBodyDeclaration(JavaParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION);
            classOrInterfaceBodyDeclaration.add(internalState.computeDeclaration);

            node.add(classOrInterfaceBodyDeclaration);

            ASTCompilationUnit compilationUnit =
                    (ASTCompilationUnit)
                            node.findFirstUpById(JavaParserTreeConstants.JJTCOMPILATIONUNIT);

            ASTImportDeclaration importDeclaration = tool.createImport(
                    tool.createName("ccbus", "desktop", "worker", "WorkerCallback")
            );
            compilationUnit.add(importDeclaration);

            importDeclaration = tool.createImport(
                    tool.createName("ccbus", "desktop", "worker", "WorkerService")
            );
            compilationUnit.add(importDeclaration);

            ASTImportDeclaration existingImport=(ASTImportDeclaration)
                    compilationUnit.findNextDownById(JavaParserTreeConstants.JJTIMPORTDECLARATION,1);

            String[] workerServiceConnect=new String[]{"ccbus","connect","core","ccbus","WorkerService"};
            while(null!=existingImport)
            {
                ASTName name=(ASTName)
                        existingImport.findFirstDownById(JavaParserTreeConstants.JJTNAME,2);

                if(tool.matchPackagePrefix(name,workerServiceConnect))
                {
                    existingImport.detach();
                }

                existingImport=(ASTImportDeclaration)
                        compilationUnit.findNextDownById(JavaParserTreeConstants.JJTTYPEPARAMETER,1);
            }
            compilationUnit.resetNextSearch();
        }
        return null;
    }

    public Node translateWorker(Node node, ClassOrInterfaceBody.InternalState internalState)
    {


        ASTClassOrInterfaceBody classOrInterfaceBody = (ASTClassOrInterfaceBody)
                node.findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACEBODY, 1);

        ASTExtendsList extendsList = (ASTExtendsList)
                node.findFirstDownById(JavaParserTreeConstants.JJTEXTENDSLIST, 1);

        ASTTypeArguments typeArguments = (ASTTypeArguments)
                extendsList.findFirstDownById(JavaParserTreeConstants.JJTTYPEARGUMENTS, 2);


        ASTIdentifier identifier = (ASTIdentifier)
                node.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER, 1);
        String name = identifier.image();
        internalState.className = name;

        for (int i = classOrInterfaceBody.jjtGetNumChildren() - 1; i >= 0; i--)
        {
            if (JavaParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION != classOrInterfaceBody.jjtGetChild(i).getId())
            {
                continue;
            }
            ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclaration = (ASTClassOrInterfaceBodyDeclaration) classOrInterfaceBody.jjtGetChild(i);
            ASTMethodDeclaration methodDeclaration = (ASTMethodDeclaration)
                    classOrInterfaceBodyDeclaration.findFirstDownById(JavaParserTreeConstants.JJTMETHODDECLARATION, 1);
            if (null != methodDeclaration)
            {
                ((Tool) internalState.tree.tool()).symTabStack().push(methodDeclaration.symTab());
                ASTIdentifier identifierMethod = (ASTIdentifier)
                        methodDeclaration.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER, 2);
                if (identifierMethod.image().equals("compute"))
                {
                    translateWorkerServer(node, classOrInterfaceBodyDeclaration, name, typeArguments, internalState);
                } else
                {
                    // Should throw error
                    //tool.errorTranslate(identifierMethod,"WorkerServer method not declared");
                }
                ((Tool) internalState.tree.tool()).symTabStack().pop();
            }
        }
        classOrInterfaceBody.resetNextSearch();
        return null;
    }

    public ASTTypeDeclaration translateWorkerServer(Node node, ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclaration,
                                                    String className, ASTTypeArguments typeArguments, ClassOrInterfaceBody.InternalState internalState)
    {


        String classParentName = className;


        // fetch package prefix
        ASTCompilationUnit compilationUnit =
                (ASTCompilationUnit)
                        node.findFirstUpById(JavaParserTreeConstants.JJTCOMPILATIONUNIT);
        ArrayList<String> packageSuffix = tool.parseCompilationUnitRelativePathClient(compilationUnit);

        // create new Worker Server class for java
        String serverWorkerClassName = classParentName;
        String serverEndPoint = serverWorkerClassName;

        // remove components package
        //packageSuffix.remove(0);
        // build with prefix
        if (packageSuffix.size() > 0)
        {
            serverEndPoint = String.join(".", packageSuffix) + "." + serverWorkerClassName;
        }
        initEndpoint(serverEndPoint, internalState);
        initResponseType(typeArguments, internalState);

        Node serverCompilationUnit = tool.getCompilationUnitNodePair().createServerCompilationUnit();
        ASTTypeDeclaration typeDeclaration = tool.createClass(serverWorkerClassName, "WorkerServerImpl");
        ASTClassOrInterfaceType classOrInterfaceType =
                (ASTClassOrInterfaceType)
                        typeDeclaration.findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACETYPE, 4);
        // do not detach
      //  typeArguments.detach();
        classOrInterfaceType.add(typeArguments);

        // create package

        ASTPackageDeclaration packageDeclaration = tool.createPackageDeclarationWorkerService(packageSuffix.toArray(new String[0]));
        serverCompilationUnit.add(packageDeclaration);

        ASTImportDeclaration importDeclaration = tool.createImport(
                tool.createName("ccbus", "connect", "core", "ccbus", "WorkerServerImpl")
        );

        serverCompilationUnit.add(importDeclaration);

        // add class type
        serverCompilationUnit.add(typeDeclaration);

        ASTClassOrInterfaceBody classOrInterfaceBody =
                (ASTClassOrInterfaceBody)
                        typeDeclaration.findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACEBODY, 2);

        translateWorkerServerFields(node, classOrInterfaceBodyDeclaration, classOrInterfaceBody, internalState);

        classOrInterfaceBodyDeclaration.detach();
        classOrInterfaceBody.add(classOrInterfaceBodyDeclaration);

        return typeDeclaration;
    }

    private void translateWorkerServerFields(Node node,
                                             ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclaration,
                                             ASTClassOrInterfaceBody classOrInterfaceBodyResult, ClassOrInterfaceBody.InternalState internalState

    )
    {

        ASTMethodDeclaration methodDeclaration = (ASTMethodDeclaration)
                classOrInterfaceBodyDeclaration.findFirstDownById(JavaParserTreeConstants.JJTMETHODDECLARATION, 2);

        ASTBlock block = (ASTBlock)
                methodDeclaration.findFirstDownById(JavaParserTreeConstants.JJTBLOCK, 1);
        if (null == block)
        {
            return;
        }
        ASTPrimaryExpression primaryExpression;
        do
        {
            primaryExpression =
                    (ASTPrimaryExpression)
                            block.findNextDownById(JavaParserTreeConstants.JJTPRIMARYEXPRESSION, 60);

            translateWorkerServerPrimaryExpression(primaryExpression, node, classOrInterfaceBodyDeclaration, classOrInterfaceBodyResult, internalState);
        }
        while (null != primaryExpression);
        if (null != primaryExpression)
        {
            primaryExpression.resetNextSearch();
        }
    }

    private void translateWorkerServerPrimaryExpression(ASTPrimaryExpression primaryExpression, Node node,
                                                        ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclaration,
                                                        ASTClassOrInterfaceBody classOrInterfaceBodyResult, ClassOrInterfaceBody.InternalState internalState)
    {
        if (null == primaryExpression)
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

        if (null != primarySuffix)
        {
            ASTPrimaryExpression primaryExpressionInSuffix =
                    (ASTPrimaryExpression)
                            primarySuffix.findNextDownById(JavaParserTreeConstants.JJTPRIMARYEXPRESSION, 30);
            translateWorkerServerPrimaryExpression(primaryExpressionInSuffix, node, classOrInterfaceBodyDeclaration, classOrInterfaceBodyResult, internalState);
        }
    }

    private void translateWorkerServerFieldsPrefix(Node node,
                                                   ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclaration,
                                                   ASTClassOrInterfaceBody classOrInterfaceBodyResult,
                                                   ASTPrimaryPrefix primaryPrefix, ClassOrInterfaceBody.InternalState internalState

    )
    {
        ASTName name =
                (ASTName)
                        primaryPrefix.findFirstDownById(JavaParserTreeConstants.JJTNAME, 1);
        if (null == name)
        {
            return;
        }
        ASTIdentifier identifier =
                (ASTIdentifier)
                        name.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER, 1);
        SymTabEntry entry = internalState.symTabStack.lookup(identifier.image());
        if (null != entry && entry.getSymTab().isClass())
        {
            if (internalState.dataTranslated.containsKey(entry.getName()))
            {
                return;
            }
            internalState.dataTranslated.put(entry.getName(), true);
            ASTType type = (ASTType) entry.getAttribute(SymTabKeyImpl.PARSED_VAR_TYPE);
            Node declaratorId = (Node) entry.getAttribute(SymTabKeyImpl.PARSED_VAR_DECLARATOR_ID);
            ASTFieldDeclaration fieldDeclaration = new ASTFieldDeclaration(JavaParserTreeConstants.JJTFIELDDECLARATION);
            fieldDeclaration.add(type);
            fieldDeclaration.add(declaratorId);

            ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclarationResult =
                    new ASTClassOrInterfaceBodyDeclaration(JavaParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION);

            ASTModifiers modifiers = new ASTModifiers(JavaParserTreeConstants.JJTMODIFIERS);
            ASTModifier modifier = new ASTModifier(JavaParserTreeConstants.JJTMODIFIER);

            tool.createToken(JavaParserConstants.PUBLIC, "public", modifier);
            modifiers.add(modifier);

            classOrInterfaceBodyDeclarationResult.add(modifiers);
            classOrInterfaceBodyDeclarationResult.add(fieldDeclaration);
            classOrInterfaceBodyResult.add(classOrInterfaceBodyDeclarationResult);

            // create payload here
            ASTPrimaryExpression primaryExpression =
                    (ASTPrimaryExpression)
                            primaryPrefix.findFirstUpById(JavaParserTreeConstants.JJTPRIMARYEXPRESSION, 30);
            translateClientData(primaryExpression, type, entry.getName(), internalState);
        }
    }

    public void translateClientData(ASTPrimaryExpression primaryExpression, ASTType type, String name, ClassOrInterfaceBody.InternalState internalState)
    {

        /*
        @Override
    public  void compute(WorkerCallback<Void> callback)
    {
        ServiceSome ref=this;

        this.setPayload(new Object()
        {
            public int val=ref.ival;
        },"somePayload");
        super.compute(callback);
    }
         */

        TokenList tokenList = tool.createTokenList(tab+tTab+"public ",type.image()," ",name, " = ref.",name,";", "\n");
        if(null==internalState.data)
        {
            internalState.data=tokenList;
        }
        else
        {
            internalState.data = tool.mergeTokenList(internalState.data, tokenList);
        }

    }


    private void buildCompute(ClassOrInterfaceBody.InternalState internalState)
    {

        TokenList tokenList = tool.createTokenList("void compute(WorkerCallback<",internalState.argumantName,"> callback)", "\n");
        TokenList tokenListBody = tool.createTokenList(tab + "{", "\n", dTab, internalState.className, " ref=this;", "\n");
        tokenList = tool.mergeTokenList(tokenList, tokenListBody);

        tokenListBody = tool.createTokenList(dTab + "this.setPayload(", "\n",tTab+"new Object()", "\n", tTab + "{","\n");
        tokenList = tool.mergeTokenList(tokenList, tokenListBody);
        if(null!=internalState.data)
        {
            tokenList = tool.mergeTokenList(tokenList, internalState.data);
        }
        tokenList = tool.mergeTokenList(tokenList,  tool.createTokenList(tTab + "}", "\n"));

        tokenList = tool.mergeTokenList(tokenList,  internalState.argumentType);
        tokenList = tool.mergeTokenList(tokenList,  internalState.endPoint);

        if(null!=internalState.responseType)
        {
            tokenList = tool.mergeTokenList(tokenList,  internalState.responseType);
        }

        tokenList = tool.mergeTokenList(tokenList,  tool.createTokenList(dTab + ");", "\n"));
        tokenList = tool.mergeTokenList(tokenList,  tool.createTokenList(dTab + "super.compute(callback);", "\n"));

        tokenList = tool.mergeTokenList(tokenList,  tool.createTokenList(tab + "}", "\n"));

        internalState.computeDeclaration.jjtSetFirstToken(tokenList.getHead());
        internalState.computeDeclaration.jjtSetLastToken(tokenList.getTail());


    }


    private void initEndpoint(String className,ClassOrInterfaceBody.InternalState internalState)
    {
        internalState.endPoint=tool.createTokenList(tTab+",",'"'+className+'"', "\n");
    }

    private void initResponseType(ASTTypeArguments typeArguments,ClassOrInterfaceBody.InternalState internalState)
    {
        ASTIdentifier identifier=(ASTIdentifier)typeArguments.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,5);
        String completeType=identifier.image();
        internalState.argumantName=completeType;
        internalState.argumentType=tool.createTokenList(tTab+",",completeType,".class", "\n");

        if(!completeType.equalsIgnoreCase("FileDownload"))
        {
            return;
        }
        // File Download should be http blob response type. By default json
        String responseType="blob";
        internalState.responseType=tool.createTokenList(tTab+",",'"'+responseType+'"', "\n");
    }

}
