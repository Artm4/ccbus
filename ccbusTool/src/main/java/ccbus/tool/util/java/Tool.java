package ccbus.tool.util.java;

import ccbus.tool.config.parser.IniParser;
import ccbus.tool.intermediate.*;
import ccbus.tool.intermediate.Node;
import ccbus.tool.intermediate.SimpleNode;
import ccbus.tool.intermediate.symtabimpl.SymTabKeyImpl;
import ccbus.tool.parser.java.*;
import ccbus.tool.parser.java.Token;
import ccbus.tool.translator.java2reactserverjava.AllowedType;
import ccbus.tool.translator.java2reactserverjava.CompilationUnitNodePair;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tool extends ccbus.tool.util.Tool
{
    static Tool instance;
    private String fileSeparator;
    IniParser configParser;
    private SymTabStack symTabStack;
    private AllowedType allowedType;
    private ParsedNamespaceTab parsedNamespaceTab;

    private CompilationUnitNodePair compilationUnitNodePair;

    private String[] rootPackageSplit;

    public ArrayList<Functor<Node> >postAction=new ArrayList<>();

    public static String connectPackageTag="ccbus";

    public static String[] platformSupported={"web","mobile","desktop"};
    public static String webPackageTag="web";
    public static String payloadPackageTag="payload";
    public static String servicePackageTag="service";
    public static String componentPackageTag="components";

    public int extendDeclarationCnt=0;
    public Node lastExtendNode=null;

    private String[][] clientPackageSuffixImage={
            {connectPackageTag,webPackageTag},
            {connectPackageTag,payloadPackageTag},
            {connectPackageTag,servicePackageTag}
    };

    private String[] serverPackageSuffixImage={
            connectPackageTag,"worker",webPackageTag
            //connectPackageTag,"worker"
    };
    private String[] serverPackageWorkerImage={
            connectPackageTag,"worker"
    };
    private String[] systemPackagePrefix={
            connectPackageTag,"connect","system"
    };

    private String[] ecmaPackageSuffix={
            connectPackageTag,"connect","ecma"
    };


    private String[] serverPackage={};
    private String[] clientPackage={};
    private String[] workerPackage={};
    private String[] ccbusPackageBase={};
    private String[][] ccbusClientPackage={{},{},{},{}};

    private String[][] ccbusPackage=
    {
        {connectPackageTag,"connect","core"},
        {connectPackageTag,"connect","web"},
        {connectPackageTag,"connect","mobile"},
        {connectPackageTag,"connect","ios"},
        {connectPackageTag,"desktop"},
    };

    // look in the root path
    public static String configPath="config.ini";

    public static void configPath(String path)
    {
        configPath=path;
    }

    public void firePostActions()
    {
        for(Functor n : postAction)
        {
            n.apply();
        }
        postAction=new ArrayList<>();
    }

    public String getFileSeparator()
    {
        return fileSeparator;
    }

    public static Tool instance() throws IOException,Exception
    {
        if(null==instance)
        {
            instance=new Tool(configPath);
        }
        return instance;
    }

    private void init(IniParser parser)
    {
        configParser=parser;
        this.fileSeparator = System.getProperty("file.separator");
        this.symTabStack=SymTabFactory.createSymTabStack();

        webPackageTag=configParser.getClientPlatform();

        this.clientPackageSuffixImage[0][1]=webPackageTag;
        this.serverPackageSuffixImage[2]=webPackageTag;
        // Push some pre build Types
        initBuildInTypes();

        this.parsedNamespaceTab=new ParsedNamespaceTab();

        this.allowedType=new AllowedType(this);

        //String rootPackagePlain=configParser.getProjectRootPackage()+"."+connectPackageTag;
        String rootPackagePlain=configParser.getProjectRootPackage();
        rootPackageSplit=rootPackagePlain.split("\\.");

        serverPackage=mergeArrays(rootPackageSplit,serverPackageSuffixImage);
        workerPackage=mergeArrays(rootPackageSplit,serverPackageWorkerImage);
        ccbusPackageBase=mergeArrays(rootPackageSplit,new String[]{connectPackageTag});
        int i;
        for(i=0;i<clientPackageSuffixImage.length;i++)
        {
            ccbusClientPackage[i] = mergeArrays(rootPackageSplit, clientPackageSuffixImage[i]);
        }
        ccbusClientPackage[i]=rootPackageSplit;

        String rootClientPackagePlain=getConfigParser().getClientRootPackage();
        if(null!=rootClientPackagePlain)
        {
            clientPackage=rootClientPackagePlain.split("\\.");
        }

    }

    public Tool(IniParser parser) throws IOException,Exception
    {
        init(parser);
    }

    public Tool(String path) throws IOException,Exception
    {
        IniParser parser=new IniParser(new FileReader(path));
        parser.parse();
        init(parser);
    }

    public CompilationUnitNodePair getCompilationUnitNodePair()
    {
        return compilationUnitNodePair;
    }

    public void setCompilationUnitNodePair(CompilationUnitNodePair compilationUnitNodePair)
    {
        this.compilationUnitNodePair = compilationUnitNodePair;
    }

    private void enterMethodSymTabEntry(SymTab symTab, String method)
    {
        SymTabEntry symTabEntry=symTab.enter(method);
        symTabEntry.setAttribute(SymTabKeyImpl.METHOD,SymTabKeyImpl.METHOD);
    }

    private void initBuildInTypes()
    {
        SymTab symTab;
        SymTabEntry symTabEntry;

        symTab=this.symTabStack.push("String");
        enterMethodSymTabEntry(symTab,"length");
        enterMethodSymTabEntry(symTab,"matches");
        enterMethodSymTabEntry(symTab,"replace");
        enterMethodSymTabEntry(symTab,"split");
        enterMethodSymTabEntry(symTab,"join");
        enterMethodSymTabEntry(symTab,"compareTo");
        this.symTabStack.pop();

        symTab=this.symTabStack.push("HashMap");
        enterMethodSymTabEntry(symTab,"put");
        enterMethodSymTabEntry(symTab,"containsKey");
        enterMethodSymTabEntry(symTab,"get");
        enterMethodSymTabEntry(symTab,"remove");
        this.symTabStack.pop();

        symTab=this.symTabStack.push("ArrayList");
        enterMethodSymTabEntry(symTab,"size");
        enterMethodSymTabEntry(symTab,"add");
        enterMethodSymTabEntry(symTab,"get");
        enterMethodSymTabEntry(symTab,"remove");
        enterMethodSymTabEntry(symTab,"contains");
        enterMethodSymTabEntry(symTab,"indexOf");
        enterMethodSymTabEntry(symTab,"subList");
        this.symTabStack.pop();

        symTab=this.symTabStack.push("List");
        enterMethodSymTabEntry(symTab,"size");
        enterMethodSymTabEntry(symTab,"add");
        enterMethodSymTabEntry(symTab,"get");
        enterMethodSymTabEntry(symTab,"remove");
        enterMethodSymTabEntry(symTab,"contains");
        enterMethodSymTabEntry(symTab,"subList");
        this.symTabStack.pop();

        symTab=this.symTabStack.push("Function");
        enterMethodSymTabEntry(symTab,"apply");
        this.symTabStack.pop();

        symTab=this.symTabStack.push("BiFunction");
        enterMethodSymTabEntry(symTab,"apply");
        this.symTabStack.pop();

        this.symTabStack.push("Object");
        this.symTabStack.pop();

        symTab=this.symTabStack.push("DateTime");
        enterMethodSymTabEntry(symTab,"toString");
        this.symTabStack.pop();

        symTab=this.symTabStack.push("Date");
        enterMethodSymTabEntry(symTab,"toString");
        this.symTabStack.pop();
    }

    public String[] getClientPackage()
    {
        return clientPackage;
    }

    public String[] getCcbusPackageBase()
    {
        return ccbusPackageBase;
    }

    public String[] getRootPackageSplit()
    {
        return rootPackageSplit;
    }

    public IniParser getConfigParser()
    {
        return configParser;
    }

    public Node parsePackageName(ASTName name)
    {
        return Misc.parsePackageName(name,this);
    }

    public Node parseFile(String file)
    {
        return Misc.parseFile(file,this);
    }

    public ParsedNamespaceTab parsedNamespaceTab(){return parsedNamespaceTab;}

    public SymTabStack symTabStack()
    {
        return symTabStack;
    }

    public AllowedType allowedType(){return allowedType;}

    public String[] getServerWorkerPackageForPlatform()
    {
        return workerPackage;
    }

    public String[] getServerWorkerPackage()
    {
        return workerPackage;
    }

    private void enterSymTab(ASTType type, ASTIdentifier identifier, ASTVariableDeclaratorId variableDeclaratorId)
    {
        // TO DO
        // Should be implemented to capture inner classes
        // Capture now only first identifier
        ASTIdentifier referenceName=(ASTIdentifier) type.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,3);
        ASTPrimitiveType primitiveType=(ASTPrimitiveType) type.findFirstDownById(JavaParserTreeConstants.JJTPRIMITIVETYPE,2);
        int arrayDim=0;

        // Capture array dim from type
        ASTBracket bracket;
        do
        {
            bracket=(ASTBracket)type.findNextDownById(JavaParserTreeConstants.JJTBRACKET,2);
            if(null!=bracket)
            {
                arrayDim++;
            }
        }
        while(null!=bracket);
        type.resetNextSearch();

        // Capture array dim from varaiable declarator
        if(null!=variableDeclaratorId)
        {
            do
            {
                bracket = (ASTBracket) variableDeclaratorId.findNextDownById(JavaParserTreeConstants.JJTBRACKET, 1);
                if (null != bracket)
                {
                    arrayDim++;
                }
            }
            while (null != bracket);
            variableDeclaratorId.resetNextSearch();
        }

        SymTabEntry symTabEntry=symTabStack.enterLocal(identifier.image());
        if(arrayDim>0)
        {
            symTabEntry.setAttribute(SymTabKeyImpl.ARRAY_DIM,arrayDim);
        }

        if(null!=referenceName)
        {
            symTabEntry.setAttribute(SymTabKeyImpl.TYPE_NAME, referenceName.image());
            symTabEntry.setAttribute(SymTabKeyImpl.REFERENCE_TYPE, referenceName.image());
        }

        if(null!=primitiveType)
        {
            symTabEntry.setAttribute(SymTabKeyImpl.TYPE_NAME, primitiveType.image());
            symTabEntry.setAttribute(SymTabKeyImpl.PRIMITIVE_TYPE, primitiveType.image());
        }

        symTabEntry.setAttribute(SymTabKeyImpl.PARSED_VAR_TYPE,type);
        // variableDeclaratorId no defined in for range declaration
        if(null!=variableDeclaratorId)
        {
            symTabEntry.setAttribute(SymTabKeyImpl.PARSED_VAR_DECLARATOR_ID, variableDeclaratorId);
        }
    }

    private void enterSymTab(ASTType type,ASTVariableDeclaratorId variableDeclaratorId)
    {
        ASTIdentifier identifier=(ASTIdentifier)
                variableDeclaratorId.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,2);
        enterSymTab(type,identifier,variableDeclaratorId);
    }

    private void enterSymTabFromVariableNode(SimpleNode variableNode)
    {
        ASTType type=(ASTType) variableNode.findFirstDownById(JavaParserTreeConstants.JJTTYPE,1);
        ASTVariableDeclaratorId variableDeclaratorId;
        do
        {
            variableDeclaratorId=(ASTVariableDeclaratorId)
                    variableNode.findNextDownById(JavaParserTreeConstants.JJTVARIABLEDECLARATORID,2);
            if(null!=variableDeclaratorId) {
                this.enterSymTab(type, variableDeclaratorId);
            }
        }
        while(null!=variableDeclaratorId);
        variableNode.resetNextSearch();
    }

    public void enterSymTabRange(Node forStatementRange)
    {
        ASTType type=(ASTType) forStatementRange.findFirstDownById(JavaParserTreeConstants.JJTTYPE,1);
        ASTIdentifier identifier=(ASTIdentifier)
                forStatementRange.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,1);
        enterSymTab(type,identifier,null);
    }

    public void enterSymTabField(Node fieldDeclaration)
    {
        this.enterSymTabFromVariableNode((SimpleNode) fieldDeclaration);
    }

    public void enterSymTabMethod(Node methodDeclarator)
    {
        ASTIdentifier identifier=(ASTIdentifier)
                methodDeclarator.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,1);

        SymTabEntry symTabEntry=symTabStack.enterClass(identifier.image());
        symTabEntry.setAttribute(SymTabKeyImpl.METHOD,SymTabKeyImpl.METHOD);
    }

    public void enterSymTabMethod(Node methodDeclarator, Node returnIdentifier)
    {
        ASTIdentifier identifier=(ASTIdentifier)
                methodDeclarator.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,1);

        SymTabEntry symTabEntry=symTabStack.enterClass(identifier.image());
        symTabEntry.setAttribute(SymTabKeyImpl.METHOD,SymTabKeyImpl.METHOD);
        if(null!=returnIdentifier)
        {
            symTabEntry.setAttribute(SymTabKeyImpl.TYPE_NAME, returnIdentifier.image());
        }
    }

    public void enterSymTabMethodLocal(Node localVariableDeclaration)
    {
        this.enterSymTabFromVariableNode((SimpleNode)localVariableDeclaration);
    }

    public void enterSymTabMethodFormal(Node formalParameter)
    {
        this.enterSymTabFromVariableNode((SimpleNode ) formalParameter);
    }

    public void enterSymTabMethodFormalParameters(Node formalParameters)
    {
        ASTFormalParameter formalParameter;
        do
        {
            formalParameter=(ASTFormalParameter)
                    formalParameters.findNextDownById(JavaParserTreeConstants.JJTFORMALPARAMETER,1);
            this.enterSymTabFromVariableNode(formalParameter);
        }
        while(null!=formalParameter);
        formalParameters.resetNextSearch();
    }

    public void addImportHash(Node name,Node star)
    {
        String nameStr=Misc.nameNodeToString(name);
        if(null!=star)
        {
            if(nameStr.equals("java.lang"))
            {
                symTabStack.addImportHash("String","java.lang.String");
            }
            else
            if(nameStr.equals("java.util"))
            {
                symTabStack.addImportHash("Date","java.util.Date");
                symTabStack.addImportHash("List","java.util.List");
                symTabStack.addImportHash("ArrayList","java.util.ArrayList");
                symTabStack.addImportHash("HashMap","java.util.HashMap");
            }
            else
            if(nameStr.equals("java.util.function"))
            {
                symTabStack.addImportHash("BiFunction","java.util.function.BiFunction");
                symTabStack.addImportHash("Function","java.util.function.Function");
            }
            else
            if(nameStr.equals("org.joda.time"))
            {
                symTabStack.addImportHash("DateTime","org.joda.time.DateTime");
            }
        }
        else
        {
            int lastDot=nameStr.lastIndexOf(".");
            String typeName=lastDot>0?nameStr.substring(lastDot+1):nameStr;
            symTabStack.addImportHash(typeName,nameStr);

        }
    }

    public Node linkNodes(List<Node> nodes)
    {
        if(0==nodes.size())
        {
            return null;
        }
        for(int i=0;i<nodes.size()-1;i++)
        {
            nodes.get(i).add(nodes.get(i+1));
        }
        return nodes.get(0);
    }

    public ASTDotToken createNodeDot()
    {
        ASTDotToken dotNode=new ASTDotToken(JavaParserTreeConstants.JJTDOTOKEN);
        ccbus.tool.parser.java.Token dotToken= ccbus.tool.parser.java.Token.newToken(
                JavaParserConstants.DOT, ".");
        dotNode.jjtSetFirstToken(dotToken);
        dotNode.jjtSetLastToken(dotToken);
        return dotNode;
    }

    public ASTIdentifier createNodeIdentifier(String image)
    {
        ASTIdentifier identifierResult=new ASTIdentifier(JavaParserTreeConstants.JJTIDENTIFIER);
        ccbus.tool.parser.java.Token tokenIdentifier= ccbus.tool.parser.java.Token.newToken(
                JavaParserConstants.IDENTIFIER, image);
        identifierResult.jjtSetFirstToken(tokenIdentifier);
        identifierResult.jjtSetLastToken(tokenIdentifier);
        return identifierResult;
    }

    public ASTCommaToken createNodeComma()
    {
        ASTCommaToken result=new ASTCommaToken(JavaParserTreeConstants.JJTCOMMATOKEN);
        ccbus.tool.parser.java.Token dotToken= ccbus.tool.parser.java.Token.newToken(
                JavaParserConstants.COMMA, ",");
        result.jjtSetFirstToken(dotToken);
        result.jjtSetLastToken(dotToken);
        return result;
    }

    public ASTLBracketToken createNodeLBracket()
    {
        ASTLBracketToken result=new ASTLBracketToken(JavaParserTreeConstants.JJTLBRACKETTOKEN);
        ccbus.tool.parser.java.Token dotToken= ccbus.tool.parser.java.Token.newToken(
                JavaParserConstants.LBRACKET, "[");
        result.jjtSetFirstToken(dotToken);
        result.jjtSetLastToken(dotToken);
        return result;
    }

    public ASTThisToken createNodeThis()
    {
        ASTThisToken result=new ASTThisToken(JavaParserTreeConstants.JJTTHISTOKEN);
        createToken(
                JavaParserConstants.THIS, "this",result);

        return result;
    }

    public ASTLiteral createNodeLiteral(String literal)
    {
        ASTLiteral result=new ASTLiteral(JavaParserTreeConstants.JJTLITERAL);
        createToken(
                JavaParserConstants.STRING_LITERAL, "\""+literal+"\"",result);

        return result;
    }

    public ASTRBracketToken createNodeRBracket()
    {
        ASTRBracketToken result=new ASTRBracketToken(JavaParserTreeConstants.JJTRBRACKETTOKEN);
        createToken(
                JavaParserConstants.RBRACKET, "]",result);

        return result;
    }

    public ASTLParenToken createNodeLParen()
    {
        ASTLParenToken result=new ASTLParenToken(JavaParserTreeConstants.JJTLPARENTOKEN);
        createToken(
                JavaParserConstants.LPAREN, "(",result);

        return result;
    }

    public ASTRParenToken createNodeRParen()
    {
        ASTRParenToken result=new ASTRParenToken(JavaParserTreeConstants.JJTRPARENTOKEN);
        createToken(
                JavaParserConstants.RPAREN, ")",result);

        return result;
    }

    public ASTTypeDeclaration createClass(String name)
    {
        return createClass(name,"");
    }

    public Token createToken(int kind,String name,Node node)
    {
        Token nameId=Token.newToken(kind,name);
        node.jjtSetFirstToken(nameId);
        node.jjtSetLastToken(nameId);
        return nameId;
    }

    public TokenList createTokenList(String ...name)
    {
        return createTokenList(JavaParserConstants.IDENTIFIER,name);
    }

    public TokenList createTokenList(int kind,String ...name)
    {
        if(name.length==0)
        {
            return null;
        }
        Token head=Token.newToken(kind,name[0]);
        Token tail=head;
        for(int i=1;i<name.length;i++)
        {
            Token current=Token.newToken(kind,name[i]);
            tail.next=current;
            tail=current;
        }

        TokenList tokenList=new TokenList(head,tail);

        return tokenList;
    }

    public TokenList mergeTokenList(TokenList left,TokenList right)
    {
        left.getTail().next=right.getHead();
        return new TokenList(left.getHead(),right.getTail());
    }

    public Node createNode(Node node,int tokenKind,String tokenImage)
    {
        createToken(tokenKind,tokenImage,node);
        return node;
    }

    public ASTTypeDeclaration createClass(String name,String extend)
    {
        ASTTypeDeclaration typeDeclaration=
                new ASTTypeDeclaration(JavaParserTreeConstants.JJTTYPEDECLARATION);

        ASTClassOrInterfaceDeclaration classOrInterfaceDeclaration=
                new ASTClassOrInterfaceDeclaration(JavaParserTreeConstants.JJTCLASSORINTERFACEDECLARATION);


        ASTModifiers modifiers=new ASTModifiers(JavaParserTreeConstants.JJTMODIFIERS);
        ASTModifier modifier=new ASTModifier(JavaParserTreeConstants.JJTMODIFIER);

        createToken(JavaParserConstants.PUBLIC,"public",modifier);
        modifiers.add(modifier);

        typeDeclaration.add(modifiers);

        ASTTypeIdentifier typeIdentifier=new ASTTypeIdentifier(JavaParserTreeConstants.JJTTYPEIDENTIFIER);
        createToken(JavaParserConstants.CLASS,"class",typeIdentifier);
        classOrInterfaceDeclaration.add(typeIdentifier);

        ASTIdentifier identifier=new ASTIdentifier(JavaParserTreeConstants.JJTIDENTIFIER);
        createToken(JavaParserConstants.IDENTIFIER,name,identifier);

        classOrInterfaceDeclaration.add(identifier);

        if(extend.length()>0)
        {
            ASTExtendsList extendsList=new ASTExtendsList(JavaParserTreeConstants.JJTEXTENDSLIST);
            ASTClassOrInterfaceType classOrInterfaceType=createClassOrInterfaceType(extend);
            extendsList.add(classOrInterfaceType);
            classOrInterfaceDeclaration.add(extendsList);
        }

        typeDeclaration.add(classOrInterfaceDeclaration);

        ASTClassOrInterfaceBody classOrInterfaceBody=
                new ASTClassOrInterfaceBody(JavaParserTreeConstants.JJTCLASSORINTERFACEBODY);
        classOrInterfaceDeclaration.add(classOrInterfaceBody);

        return typeDeclaration;

    }

    public ASTClassOrInterfaceType createClassOrInterfaceType(String name)
    {
        ASTClassOrInterfaceType classOrInterfaceType=
                new ASTClassOrInterfaceType(JavaParserTreeConstants.JJTCLASSORINTERFACETYPE);

        ASTIdentifier identifier=new ASTIdentifier(JavaParserTreeConstants.JJTIDENTIFIER);
        Token nameId=Token.newToken(JavaParserConstants.IDENTIFIER,name);
        identifier.jjtSetFirstToken(nameId);
        identifier.jjtSetLastToken(nameId);
        classOrInterfaceType.add(identifier);

        return classOrInterfaceType;
    }

    public ASTAllocationExpression createAllocationExpressionClassDeclaration(String name)
    {
        ASTAllocationExpression allocationExpression=
                new ASTAllocationExpression(JavaParserTreeConstants.JJTALLOCATIONEXPRESSION);

        ASTClassOrInterfaceType classOrInterfaceType=createClassOrInterfaceType(name);
        allocationExpression.add(classOrInterfaceType);

        ASTClassOrInterfaceBody classOrInterfaceBody=
                new ASTClassOrInterfaceBody(JavaParserTreeConstants.JJTCLASSORINTERFACEBODY);
        allocationExpression.add(classOrInterfaceBody);

        SimpleNode newNode=(SimpleNode)createNode(
                    new ccbus.tool.parser.java.SimpleNode(JavaParserTreeConstants.JJTNEWTOKEN),
                    JavaParserConstants.NEW,"new"
                );

        allocationExpression.add(newNode);
        return allocationExpression;
    }

    public ASTVariableInitializer createNamelessClassExpression(String name)
    {
        ASTVariableInitializer variableInitializer=
                new ASTVariableInitializer(JavaParserTreeConstants.JJTVARIABLEINITIALIZER);

        ASTExpression expression=
                new ASTExpression(JavaParserTreeConstants.JJTEXPRESSION);
        variableInitializer.add(expression);


        ASTAllocationExpression allocationExpression=createAllocationExpressionClassDeclaration(name);

        expression.add(allocationExpression);

        return variableInitializer;
    }

    public ASTVariableInitializer createVariableInitializer(ASTPrimaryExpression primaryExpression)
    {
        ASTVariableInitializer variableInitializer=
                new ASTVariableInitializer(JavaParserTreeConstants.JJTVARIABLEINITIALIZER);

        ASTExpression expression=
                new ASTExpression(JavaParserTreeConstants.JJTEXPRESSION);
        variableInitializer.add(expression);

        expression.add(primaryExpression);

        return variableInitializer;
    }

    public ASTName createName(String ...nameList)
    {
        ASTName nameNode = new ASTName(JavaParserTreeConstants.JJTNAME);
        for (String name: nameList)
        {
            ASTIdentifier identifier = new ASTIdentifier(JavaParserTreeConstants.JJTIDENTIFIER);
            createToken(JavaParserConstants.IDENTIFIER, name, identifier);
            nameNode.add(identifier);
        }
        return nameNode;
    }

    public ASTIdentifier createIdentifier(String name)
    {
        ASTIdentifier identifier = new ASTIdentifier(JavaParserTreeConstants.JJTIDENTIFIER);
        createToken(JavaParserConstants.IDENTIFIER, name, identifier);
        return identifier;
    }

    public ASTVariableInitializer createVariableInitializer(ASTPrimaryPrefix prefix)
    {
        ASTPrimaryExpression primaryExpression=
                new ASTPrimaryExpression(JavaParserTreeConstants.JJTPRIMARYEXPRESSION);

        primaryExpression.add(prefix);

        return createVariableInitializer(primaryExpression);
    }

    public ASTPrimaryExpression createPrimaryExpression(ASTPrimaryPrefix primaryPrefix,ASTPrimarySuffix ...primarySuffixList)
    {
        ASTPrimaryExpression node=
                new ASTPrimaryExpression(JavaParserTreeConstants.JJTPRIMARYEXPRESSION);
        node.add(primaryPrefix);
        if(null!=primarySuffixList)
        {
            for (ASTPrimarySuffix primarySuffix : primarySuffixList)
            {
                node.add(primarySuffix);
            }
        }
        return node;
    }
    public ASTPrimaryExpression createPrimaryExpression(ASTPrimaryPrefix primaryPrefix)
    {

        return createPrimaryExpression(primaryPrefix,null);
    }

    public ASTMethodDeclaration createMethodDeclaration(String name)
    {
        ASTMethodDeclaration nodeResult=new ASTMethodDeclaration(JavaParserTreeConstants.JJTMETHODDECLARATION);
        ASTMethodDeclarator declarator=
                new ASTMethodDeclarator(JavaParserTreeConstants.JJTMETHODDECLARATOR);

        nodeResult.add(declarator);

        ASTIdentifier nodeIdentifier=createIdentifier(name);

        ASTFormalParameters nodeFormalParameters=(ASTFormalParameters)
                new ASTFormalParameters(JavaParserTreeConstants.JJTFORMALPARAMETERS);

        declarator.add(nodeIdentifier);
        declarator.add(nodeFormalParameters);

        return nodeResult;
    }



    public ASTPrimaryPrefix createPrimaryPrefix(Node child)
    {
        ASTPrimaryPrefix node=
                new ASTPrimaryPrefix(JavaParserTreeConstants.JJTPRIMARYPREFIX);
        node.add(child);
        return node;
    }

    public ASTPrimarySuffix createPrimarySuffix(Node ...childList)
    {
        ASTPrimarySuffix node=
                new ASTPrimarySuffix(JavaParserTreeConstants.JJTPRIMARYSUFFIX);
        for(Node child:childList)
        {
            node.add(child);
        }
        return node;
    }

    public ASTPackageDeclaration createPackageDeclaration(ASTName name,ASTModifiers modifiers)
    {
        ASTPackageDeclaration packageDeclaration=new ASTPackageDeclaration(JavaParserTreeConstants.JJTPACKAGEDECLARATION);
        if(null!=modifiers)
        {
            packageDeclaration.add(modifiers);
        }
        packageDeclaration.add(name);
        return packageDeclaration;
    }

    public ASTPackageDeclaration createPackageDeclaration(ASTName name)
    {
        return createPackageDeclaration(name,null);
    }

    public ASTPackageDeclaration createPackageDeclarationWorkerServer(String[] suffix)
    {
        String[] packageResult=mergeArrays(serverPackage,suffix);
        return createPackageDeclaration(createName(packageResult),null);
    }

    public ASTPackageDeclaration createPackageDeclarationWorkerService(String[] suffix)
    {
        String[] packageResult=mergeArrays(workerPackage,suffix);
        return createPackageDeclaration(createName(packageResult),null);
    }

    public ASTType createReferenceType(String name)
    {
        ASTType type=
                new ASTType(JavaParserTreeConstants.JJTTYPE);

        ASTReferenceType referenceType=
                new ASTReferenceType(JavaParserTreeConstants.JJTREFERENCETYPE);
        type.add(referenceType);

        ASTClassOrInterfaceType classOrInterfaceTypeReference=
                new ASTClassOrInterfaceType(JavaParserTreeConstants.JJTCLASSORINTERFACETYPE);
        referenceType.add(classOrInterfaceTypeReference);

        ASTIdentifier identifier=new ASTIdentifier(JavaParserTreeConstants.JJTIDENTIFIER);
        ccbus.tool.parser.java.Token tokenIdentifier= ccbus.tool.parser.java.Token.newToken(
                JavaParserConstants.IDENTIFIER, name);
        identifier.jjtSetFirstToken(tokenIdentifier);
        identifier.jjtSetLastToken(tokenIdentifier);

        classOrInterfaceTypeReference.add(identifier);
        return type;
    }

    public ASTClassOrInterfaceBodyDeclaration createFieldDeclaration(String name,ASTType type,ASTVariableInitializer variableInitializer)
    {
        ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclaration=
                new ASTClassOrInterfaceBodyDeclaration(JavaParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION);

        ASTFieldDeclaration fieldDeclaration=
                new ASTFieldDeclaration(JavaParserTreeConstants.JJTFIELDDECLARATION);
        classOrInterfaceBodyDeclaration.add(fieldDeclaration);

        fieldDeclaration.add(type);

        ASTVariableDeclarator variableDeclarator=
                new ASTVariableDeclarator(JavaParserTreeConstants.JJTVARIABLEDECLARATOR);
        fieldDeclaration.add(variableDeclarator);

        ASTVariableDeclaratorId variableDeclaratorId=
                new ASTVariableDeclaratorId(JavaParserTreeConstants.JJTVARIABLEDECLARATORID);
        variableDeclarator.add(variableDeclaratorId);

        ASTIdentifier identifierVariable=new ASTIdentifier(JavaParserTreeConstants.JJTIDENTIFIER);
        variableDeclaratorId.add(identifierVariable);

        this.createToken(
                JavaParserConstants.IDENTIFIER, name,identifierVariable);

        if(null!=variableInitializer)
        {
            ASTAssignToken assignmentOperator =
                    new ASTAssignToken(JavaParserTreeConstants.JJTASSIGNTOKEN);
            ccbus.tool.parser.java.Token tokenAssignmentr = ccbus.tool.parser.java.Token.newToken(
                    JavaParserConstants.IDENTIFIER, "=");
            assignmentOperator.jjtSetFirstToken(tokenAssignmentr);
            assignmentOperator.jjtSetLastToken(tokenAssignmentr);
            variableDeclarator.add(assignmentOperator);

            variableDeclarator.add(variableInitializer);
        }

        return classOrInterfaceBodyDeclaration;
    }

    public ASTClassOrInterfaceBodyDeclaration createLiteral(String name,ASTVariableInitializer variableInitializer)
    {
        ASTType type=this.createReferenceType("Object");
        ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclaration=this.createFieldDeclaration(
                name,type,variableInitializer
        );

        return classOrInterfaceBodyDeclaration;
    }

    public ASTClassOrInterfaceBodyDeclaration createLiteral(String name)
    {
        ASTVariableInitializer variableInitializer=
                this.createNamelessClassExpression("Object");
        return createLiteral(name,variableInitializer);
    }

    public ASTImportDeclaration createImport(ASTName name,boolean wild)
    {
        ASTImportDeclaration importDeclaration=new ASTImportDeclaration(JavaParserTreeConstants.JJTIMPORTDECLARATION);
        importDeclaration.add(name);
        if(wild)
        {
            ASTStar star=new ASTStar(JavaParserTreeConstants.JJTSTAR);
            createToken(JavaParserConstants.STAR,"*",star);
            importDeclaration.add(star);
        }
        return importDeclaration;
    }

    public ASTImportDeclaration createImport(ASTName name)
    {
        return createImport(name,false);
    }

    public void insertImport(Node node,ASTImportDeclaration importDeclaration)
    {
        ASTCompilationUnit compilationUnit=(ASTCompilationUnit)
                node.findFirstUpById(JavaParserTreeConstants.JJTCOMPILATIONUNIT);

         compilationUnit.add(importDeclaration);
    }


    public boolean isSystemPackage(Node name)
    {
        String packageName=Misc.nameNodeToPackageString(name);
        String systemPrefix=String.join(".",systemPackagePrefix);
        return packageName.startsWith(systemPrefix);
    }

    public boolean isEcmaPackage(Node name)
    {
        String packageName=Misc.nameNodeToPackageString(name);
        String prefix=String.join(".",ecmaPackageSuffix);
        return packageName.startsWith(prefix);
    }

    public String[][] getClientPackageSuffixImage()
    {
        return clientPackageSuffixImage;
    }

    public boolean isClientPackage(Node name)
    {
        String rootPackage=this.getConfigParser().getProjectRootPackage();
        String packageName=Misc.nameNodeToPackageString(name);
        for(int i=0;i<clientPackageSuffixImage.length;i++)
        {
            String assertPackage=rootPackage
                    +"."
                    +clientPackageSuffixImage[i][0]
                    +"."
                    +clientPackageSuffixImage[i][1];


            if(packageName.startsWith(assertPackage))
            {
                return true;
            }
        }

        for(int i=0;i<ccbusPackage.length;i++)
        {
            String prefixPackage=ccbusPackage[i][0]
                    +"."
                    +ccbusPackage[i][1];

            if(packageName.startsWith(prefixPackage))
            {
                return true;
            }
        }

        return false;
    }

    public boolean isSharePackage(Node name)
    {
        String packageName=Misc.nameNodeToPackageString(name);
        return packageName.contains("payload");
    }

    public Node cloneNode(Node node)
    {
        Node nodeResult=node.createCopy();
        for(int i=0;i<node.jjtGetNumChildren();i++)
        {
            nodeResult.add(cloneNode(node.jjtGetChild(i)));
        }
        return nodeResult;
    }

    public Node copyNodeState(Node from,Node to)
    {
        if(null!=from.jjtGetFirstToken())
        {
            to.jjtSetFirstToken(ccbus.tool.intermediate.Token.newToken(from.jjtGetFirstToken().kind, from.jjtGetFirstToken().image));
        }
        if(null!=from.jjtGetLastToken())
        {
            to.jjtSetLastToken(ccbus.tool.intermediate.Token.newToken(from.jjtGetLastToken().kind, from.jjtGetLastToken().image));
        }
        return to;
    }


    public void translateClientPackageSuffix(ASTNameEff name)
    {
        ASTIdentifier identifierFirst = (ASTIdentifier) name.findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER, 1);
        if(!identifierFirst.image().equals(connectPackageTag))
        {
            return;
        }

        ASTIdentifier identifierSecond = (ASTIdentifier) name.findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER, 1);

        // looking for payload and web package (could be mobile to do...)
        if(null==identifierSecond)
        {
            return;
        }

        if(identifierSecond.image().equals(payloadPackageTag))
        {
            identifierFirst.detach();
        }
        else
        if(identifierSecond.image().equals(servicePackageTag))
        {
            identifierFirst.detach();
        }
        else
        if(identifierSecond.image().equals(webPackageTag))
        {
            identifierFirst.detach();
            identifierSecond.detach();
        }
        name.resetNextSearch();

    }

    public ASTNameEff removePrefix(ASTName name)
    {
        name.resetNextSearch();

        // Case when compiling ccbus Package units.
//        ASTNameEff nameEffRoot=removePrefix(name,rootPackageSplit);
//        if(null!=nameEffRoot)
//        {
//            name.resetNextSearch();
//            return nameEffRoot;
//        }

        for(int i=0;i<ccbusPackage.length;i++)
        {
            ASTNameEff nameEff=removePrefix(name,ccbusPackage[i]);
            if(null!=nameEff)
            {
                name.resetNextSearch();
                return nameEff;
            }
        }
        name.resetNextSearch();
        ASTNameEff nameEff=removePrefix(name,rootPackageSplit);
        return nameEff;
    }

    // should remove prefix as ccbus.core
    // remove project package prefix for shared for client, keep it for worker.
    public ASTNameEff removePrefix(ASTName name,String[] packageList)
    {
        ASTNameEff nameEff=null;
        if(matchPackagePrefix(name,packageList))
        {
            nameEff=translateNameEff(name,packageList);
        }
        name.resetNextSearch();
        return nameEff;
    }

    private ASTNameEff translateNameEff(ASTName name,String[] packageList)
    {
        ASTNameEff nameEff=new ASTNameEff(JavaParserTreeConstants.JJTNAMEEFF);
        int dropCount=packageList.length;
        ASTIdentifier identifier = (ASTIdentifier) name.findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER, 1);
        int j=1;
        while(null!=identifier)
        {
            if(j>dropCount)
            {
                nameEff.add(identifier);
            }
            j++;
            identifier = (ASTIdentifier) name.findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER, 1);
        }
        name.resetNextSearch();
        return nameEff;
    }

    public ArrayList<String> parseCompilationUnitRelativePathClient(ASTCompilationUnit compilationUnit)
    {

        ArrayList relativePath = new ArrayList<String>();
        for(int j=0;j<ccbusClientPackage.length;j++)
        {
            relativePath=parseCompilationUnitRelativePath(compilationUnit,ccbusClientPackage[j]);
            if(null!=relativePath)
            {
                return relativePath;
            }
        }
        // should throw error
        System.out.println("Error: client package not correct");
        return relativePath;
    }

    public ArrayList<String> parseCompilationUnitRelativePathWorker(ASTCompilationUnit compilationUnit)
    {
        ArrayList relativePath = new ArrayList<String>();
        ArrayList resultService=parseCompilationUnitRelativePath(compilationUnit,workerPackage);
        if(null!=resultService)
        {
            return resultService;
        }
        ArrayList result=parseCompilationUnitRelativePath(compilationUnit,serverPackage);
        if(null!=result)
        {
            return result;
        }
        // should throw error
        System.out.println("Error: client package not correct");
        return relativePath;
    }

    public ArrayList<String> parseCompilationUnitRelativePath(ASTCompilationUnit compilationUnit,String[] packagePath)
    {
        ASTName name = findPackageName(compilationUnit);

        ArrayList relativePath = new ArrayList<String>();

        ASTIdentifier identifier = (ASTIdentifier) name.findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER, 1);
        boolean found=false;


        if (0 == packagePath.length)
        {
            // should throw error
            System.out.println("Package path not configured");
        }

        int i;
        for (i = 0; i < packagePath.length; i++)
        {
            if (
                    null == identifier
                            || false == identifier.image().equals(packagePath[i]))
            {
                break;
            }
            identifier = (ASTIdentifier) name.findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER, 1);
        }

        if(i!=packagePath.length)
        {
            name.resetNextSearch();
            return null;
        }
        else
        {
            if(packagePath[packagePath.length-1].equals(payloadPackageTag))
            {
                relativePath.add(payloadPackageTag);
            }
            else
            if(packagePath[packagePath.length-1].equals(servicePackageTag))
            {
                relativePath.add(servicePackageTag);
            }
        }

        do
        {
            if(null!=identifier)
            {
                relativePath.add(identifier.image());
            }
            identifier = (ASTIdentifier) name.findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER, 1);
        }
        while(null!=identifier);
        name.resetNextSearch();

        return relativePath;
    }

    public boolean matchPackagePrefix(ASTName name,String[] packageList)
    {
        name.resetNextSearch();
        for(int j=0;j<packageList.length;j++)
        {
            ASTIdentifier identifier = (ASTIdentifier) name.findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER, 1);
            if(null==identifier)
            {
                name.resetNextSearch();
                return false;
            }
            if(!identifier.image().equals(packageList[j]))
            {
                name.resetNextSearch();
                return false;
            }
        }
        name.resetNextSearch();
        return true;
    }

    public boolean matchPackageFirst(ASTName name,String[] packageList)
    {
        name.resetNextSearch();
        boolean foundSequenceStart=false;
        int j=0;

        ASTIdentifier identifier = (ASTIdentifier) name.findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER, 1);
        while(null!=identifier)
        {
            if(j<packageList.length && identifier.image().equals(packageList[j]))
            {
                j++;
            }
            else
            if(j==packageList.length)
            {
                break;
            }
            else
            if(j>0)
            {
                j=0;
            }

            identifier = (ASTIdentifier) name.findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER, 1);
        }
        name.resetNextSearch();
        return j==packageList.length;
    }

    public ASTName findPackageName(ASTCompilationUnit compilationUnit)
    {
        ASTPackageDeclaration packageDeclaration = (ASTPackageDeclaration)
                compilationUnit.findFirstDownById(JavaParserTreeConstants.JJTPACKAGEDECLARATION);

        ASTName name = (ASTName)
                packageDeclaration.findFirstDownById(JavaParserTreeConstants.JJTNAME,1);
        return name;
    }

    public boolean isPayloadPackage(ASTCompilationUnit compilationUnit)
    {
        return isPayloadPackage(findPackageName(compilationUnit));
    }

    public boolean isPayloadPackage(ASTName name)
    {
        //return matchPackagePrefix(name,ccbusClientPackage[1]);
        return matchPackagePrefix(name,rootPackageSplit) && matchPackageFirst(name,new String[]{payloadPackageTag});
    }

    public boolean isComponentPackage(ASTCompilationUnit compilationUnit)
    {
        return isComponentPackage(findPackageName(compilationUnit));
    }

    public boolean isComponentPackage(ASTName name)
    {
        //return matchPackagePrefix(name,ccbusClientPackage[0]);
        return matchPackagePrefix(name,rootPackageSplit)&& matchPackageFirst(name,new String[]{componentPackageTag});
    }

    public boolean isCoreSharedPackage(ASTName name)
    {
        return matchPackagePrefix(name,ccbusPackage[0]);
    }

    public boolean isDesktopPackage(ASTName name)
    {
        return matchPackagePrefix(name,ccbusPackage[4]);
    }

    public boolean isMethodCall(ASTIdentifier identifier)
    {

        Node primaryExpression=identifier.jjtGetParent().jjtGetParent();
        if(primaryExpression.getId()!=JavaParserTreeConstants.JJTPRIMARYEXPRESSION)
        {
            return false;
        }
        // Method call, no class access
        return null!=primaryExpression.findFirstDownById(JavaParserTreeConstants.JJTARGUMENTS,2);

    }

    public String fetchClassName(ASTCompilationUnit compilationUnit)
    {
        ASTClassOrInterfaceDeclaration classOrInterfaceDeclaration=
                (ASTClassOrInterfaceDeclaration)
                compilationUnit.findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACEDECLARATION,4);
        String name=classOrInterfaceDeclaration.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,1).image();
        return name;
    }

    public void error(Token currentToken,String message,ErrorCode errorCode)
    {
        String retval = errorCode.getMessage()+": "+"\""+message+"\";";
        retval += " Encountered \"";
        Token tok = currentToken;
        retval += tok.image;
        retval += "\" at line " + tok.beginLine + ", column " + tok.beginColumn;
        retval += "." + Misc.eol;
        System.err.println(retval);
        abortExecution(errorCode);
    }

    public void error(ccbus.tool.intermediate.Token currentToken, String message, ErrorCode errorCode)
    {
        String retval = errorCode.getMessage()+": "+"\""+message+"\";";
        retval += " Encountered \"";
        ccbus.tool.intermediate.Token tok = currentToken;
        retval += tok.image;
        retval += "\" at line " + tok.beginLine + ", column " + tok.beginColumn;
        retval += "." + Misc.eol;
        System.err.println(retval);
        abortExecution(errorCode);
    }

    public void error(Node node,String message,ErrorCode errorCode)
    {
        String className=this.getClassName(node);
        if(className.length()>0){System.err.print(" In class: "+className+". ");}
        error(node.jjtGetFirstToken(),message,errorCode);
    }

    public void error(ccbus.tool.parser.java.Node node, String message, ErrorCode errorCode)
    {
        String className=this.getClassName(node);
        if(className.length()>0){System.err.print(" In class: "+className+". ");}
        error(node.jjtGetFirstToken(),message,errorCode);
    }

    public void errorTranslate(Token currentToken,String message)
    {
        error(currentToken,message,ErrorCode.TRANSLATION_ERROR);
    }

    public void errorTranslate(ccbus.tool.intermediate.Token currentToken, String message)
    {
        error(currentToken,message,ErrorCode.TRANSLATION_ERROR);
    }

    public void errorTranslate(Node node,String message)
    {
        String className=this.getClassName(node);
        if(className.length()>0){System.err.print(" In class: "+className+". ");}
        error(node.jjtGetFirstToken(),message,ErrorCode.TRANSLATION_ERROR);
    }

    public void errorTranslate(ccbus.tool.parser.java.SimpleNode node, String message)
    {

        String className=this.getClassName(node);
        if(className.length()>0){System.err.print(" In class: "+className+". ");}
        error((Token)node.jjtGetFirstToken(),message,ErrorCode.TRANSLATION_ERROR);
    }

    public String getClassName(Node node)
    {
        Node classDecl= node.findFirstUpById(JavaParserTreeConstants.JJTCLASSORINTERFACEDECLARATION);
        if(null==classDecl)
        {
            return "";
        }
        Node identifier=classDecl.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER);
        return identifier.image();
    }

    public void pushExtendsSymTab(ASTExtendsList node)
    {
        ASTIdentifier identifier=(ASTIdentifier)
                node.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,2);
        String image=identifier.image();
        Node nodeParsed=parsedNamespaceTab().lookupByClassName(image);
        if(null==nodeParsed)
        {
            errorTranslate(identifier,"Identifier not declared");
        }
        ASTClassOrInterfaceBody classOrInterfaceBody=(ASTClassOrInterfaceBody)
                nodeParsed.findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACEBODY,3);
        ASTExtendsList  extendsList=(ASTExtendsList)
                nodeParsed.findFirstDownById(JavaParserTreeConstants.JJTEXTENDSLIST,3);
        if(null!=extendsList)
        {
            pushExtendsSymTab(extendsList);
        }
        this.symTabStack.push(classOrInterfaceBody.symTab());
    }

    public void popExtendsSymTab(ASTExtendsList node)
    {
        for(int i=0;i<node.jjtGetNumChildren();i++)
        {
            this.symTabStack().pop();
        }
    }

    public void renameReferenceType(ccbus.tool.parser.ecmascript.ASTIdentifier identifier)
    {
        String name=identifier.image();
        ccbus.tool.parser.ecmascript.Token tokenIdentifier= ccbus.tool.parser.ecmascript.Token.newToken(
                JavaParserConstants.IDENTIFIER, "");

        ccbus.tool.parser.ecmascript.ASTIdentifier identifierR=renameReferenceType((Node)identifier);
    }

    public void renameReferenceType(ASTIdentifier identifier)
    {
        String name=identifier.image();
        Token tokenIdentifier= Token.newToken(
                JavaParserConstants.IDENTIFIER, "");

        ASTIdentifier identifierR=renameReferenceType((Node)identifier);
    }

    public <T extends Node> T renameReferenceType(Node identifierIn)
    {
        T identifier=(T) identifierIn;
        String name=identifier.image();
        Token tokenIdentifier= Token.newToken(
                JavaParserConstants.IDENTIFIER, "");

        if(name.equals("HashMap"))
        {
            tokenIdentifier.image="Map";
            identifier.jjtSetFirstToken(tokenIdentifier);
            identifier.jjtSetLastToken(tokenIdentifier);
        }
        else
        if(name.equals("ArrayList")||name.equals("List"))
        {
            tokenIdentifier.image="Array";
            identifier.jjtSetFirstToken(tokenIdentifier);
            identifier.jjtSetLastToken(tokenIdentifier);
        }
        else
        if(name.equals("DateTime"))
        {
            tokenIdentifier.image="Date";
            identifier.jjtSetFirstToken(tokenIdentifier);
            identifier.jjtSetLastToken(tokenIdentifier);
        }
        return identifier;
    }

    public void abortExecution(ErrorCode errorCode)
    {
        System.exit(errorCode.ordinal());
    }
}