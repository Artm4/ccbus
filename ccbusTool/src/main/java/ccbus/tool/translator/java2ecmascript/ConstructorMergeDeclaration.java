package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.*;
import ccbus.tool.parser.java.ASTConstructorDeclaration;
import ccbus.tool.parser.java.JavaParserConstants;
import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.util.java.Tool;

public class ConstructorMergeDeclaration implements TreeTranslator
{
    Tool tool;

    public Node translate(Node node,TranslatedTree tree)
    {
        tool=(Tool) tree.tool();
        ASTClassOrInterfaceBodyDeclaration nodeResult=new ASTClassOrInterfaceBodyDeclaration(EcmaParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION);

        // Only one constructor
        ASTConstructorDeclaration constructorDeclaration=(ASTConstructorDeclaration) node
                .findNextDownById(JavaParserTreeConstants.JJTCONSTRUCTORDECLARATION,2);

        int ctrIndex=0;
        while(null!=constructorDeclaration)
        {
            constructorDeclaration=(ASTConstructorDeclaration) node
                    .findNextDownById(JavaParserTreeConstants.JJTCONSTRUCTORDECLARATION,2);

            ctrIndex++;
        }

        node.resetNextSearch();
        if(1==ctrIndex)
        {
            constructorDeclaration=(ASTConstructorDeclaration) node
                    .findFirstDownById(JavaParserTreeConstants.JJTCONSTRUCTORDECLARATION,2);

            ASTMethodDeclaration nodeResultMethod=(ASTMethodDeclaration)
                    (new MethodDeclaration()).translate(constructorDeclaration,tree);

            ASTIdentifier methodIdentifier=(ASTIdentifier)
                    nodeResultMethod.findFirstDownById(EcmaParserTreeConstants.JJTIDENTIFIER,2);
            tool.createToken(EcmaParserTreeConstants.JJTIDENTIFIER,"constructor",methodIdentifier);
            nodeResult.add(nodeResultMethod);
            return nodeResult;
        }


        // More than one constructors

        ASTMethodDeclaration methodResult=new ASTMethodDeclaration(EcmaParserTreeConstants.JJTMETHODDECLARATION);
        ASTMethodDeclarator nodeDeclaratorResult=new ASTMethodDeclarator(EcmaParserTreeConstants.JJTMETHODDECLARATOR);
        ASTFormalParameters formalParametersResult=new ASTFormalParameters(EcmaParserTreeConstants.JJTFORMALPARAMETERS);
        nodeDeclaratorResult.add(formalParametersResult);
        methodResult.add(nodeDeclaratorResult);
        nodeResult.add(methodResult);

        Node nodeParent=tree.translatedNode().getNode();

        ASTIdentifier methodIdentifier=new ASTIdentifier(EcmaParserTreeConstants.JJTIDENTIFIER);
        tool.createToken(EcmaParserTreeConstants.JJTIDENTIFIER,"constructor",methodIdentifier);
        nodeDeclaratorResult.add(methodIdentifier);

        ASTBlock block=new ASTBlock(EcmaParserTreeConstants.JJTBLOCK);
        methodResult.add(block);


        // Iterate all constructors and rename them
        ctrIndex=0;
        constructorDeclaration=(ASTConstructorDeclaration) node
                .findNextDownById(JavaParserTreeConstants.JJTCONSTRUCTORDECLARATION,2);
        translateBlockSuper(block,node);

        while(null!=constructorDeclaration)
        {
            ASTMethodDeclaration nodeConstructorResult=(ASTMethodDeclaration)
                    (new MethodDeclaration()).translate(constructorDeclaration,tree);

            ASTIdentifier methodIdentifierOverloadConstructor=(ASTIdentifier)
                    nodeConstructorResult.findFirstDownById(EcmaParserTreeConstants.JJTIDENTIFIER,2);

            ASTFormalParameters formalParameters=(ASTFormalParameters) nodeConstructorResult.findFirstDownById(
                EcmaParserTreeConstants.JJTFORMALPARAMETERS,2
            );

            int cntFormalParams=null!=formalParameters ? formalParameters.jjtGetNumChildren() : 0;


            String nameCtr="constructor_"+Integer.toString(ctrIndex);
            tool.createToken(EcmaParserTreeConstants.JJTIDENTIFIER,nameCtr,methodIdentifierOverloadConstructor);

            ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclarationResultItem =
                    new ASTClassOrInterfaceBodyDeclaration(EcmaParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION);

            classOrInterfaceBodyDeclarationResultItem.add(nodeConstructorResult);

            nodeParent.add(classOrInterfaceBodyDeclarationResultItem);


            translateBlock(block,methodIdentifierOverloadConstructor,cntFormalParams,tree);

            constructorDeclaration=(ASTConstructorDeclaration) node
                    .findNextDownById(JavaParserTreeConstants.JJTCONSTRUCTORDECLARATION,2);

            ctrIndex++;
        }
        return nodeResult;
    }


    private void translateBlockSuper(ASTBlock block,Node node)
    {
        //
        ccbus.tool.parser.java.ASTClassOrInterfaceDeclaration classOrInterfaceDeclaration=
                ( ccbus.tool.parser.java.ASTClassOrInterfaceDeclaration)node.jjtGetParent();
        Node extendsList=classOrInterfaceDeclaration.findFirstDownById(JavaParserTreeConstants.JJTEXTENDSLIST,1);
        if(null==extendsList)
        {
            return;
        }
        // add super if extends

        ASTStatement statementSuper=new ASTStatement(EcmaParserTreeConstants.JJTSTATEMENT);
        ASTBlockStatement blockStatementSuper=new ASTBlockStatement(EcmaParserTreeConstants.JJTBLOCKSTATEMENT);
        blockStatementSuper.add(statementSuper);

        // arguments
        ccbus.tool.parser.ecmascript.ASTArguments argumentsSuper=
                new ccbus.tool.parser.ecmascript.ASTArguments(EcmaParserTreeConstants.JJTARGUMENTS);

        argumentsSuper.add(tool.createNodeLParen());
        argumentsSuper.add(tool.createNodeRParen());


        ccbus.tool.parser.ecmascript.ASTName nameNode =
                new ccbus.tool.parser.ecmascript.ASTName(EcmaParserTreeConstants.JJTNAME);

        ccbus.tool.parser.ecmascript.ASTIdentifier identifier =
                new ccbus.tool.parser.ecmascript.ASTIdentifier(EcmaParserTreeConstants.JJTIDENTIFIER);
        tool.createToken(JavaParserConstants.IDENTIFIER, "super", identifier);
        nameNode.add(identifier);


        ccbus.tool.parser.ecmascript.ASTPrimaryPrefix primaryPrefix=
                new ccbus.tool.parser.ecmascript.ASTPrimaryPrefix(EcmaParserTreeConstants.JJTPRIMARYPREFIX);
        primaryPrefix.add(nameNode);

        ccbus.tool.parser.ecmascript.ASTPrimarySuffix primarySuffix=
                new ccbus.tool.parser.ecmascript.ASTPrimarySuffix(EcmaParserTreeConstants.JJTPRIMARYSUFFIX);
        primarySuffix.add(argumentsSuper);

        ASTPrimaryExpression primaryExpressionSuper=
                new ASTPrimaryExpression(EcmaParserTreeConstants.JJTPRIMARYEXPRESSION);

        primaryExpressionSuper.add(primaryPrefix);
        primaryExpressionSuper.add(primarySuffix);

        statementSuper.add(primaryExpressionSuper);

        // semicolon
        ASTSemicolonToken semicolonToken=new ASTSemicolonToken(EcmaParserTreeConstants.JJTSEMICOLONTOKEN);
        tool.createToken(JavaParserConstants.IDENTIFIER, ";", semicolonToken);
        statementSuper.add(semicolonToken);

        block.add(blockStatementSuper);

        // end add super
    }


    private void translateBlock(ASTBlock block,ASTIdentifier identifierCtr,int cntFormalParams, TranslatedTree tree)
    {
        ASTBlockStatement blockStatement=new ASTBlockStatement(EcmaParserTreeConstants.JJTBLOCKSTATEMENT);
        block.add(blockStatement);

        ASTIfStatement ifStatement=new ASTIfStatement(EcmaParserTreeConstants.JJTIFSTATEMENT);


        ifStatement.add(tool.createNode(new ASTRParenToken(EcmaParserTreeConstants.JJTIFTOKEN),
                EcmaParserConstants.IF,"if"));

        ifStatement.add(tool.createNode(new ASTRParenToken(EcmaParserTreeConstants.JJTLPARENTOKEN),
                EcmaParserConstants.LPAREN,"("));

        // Equality Expression
        ASTEqualityExpression equalityExpression=new ASTEqualityExpression(EcmaParserTreeConstants.JJTEQUALITYEXPRESSION);
        equalityExpression.add(
                tool.createPrimaryExpression(
                        tool.createPrimaryPrefix(tool.createName("arguments")),
                        tool.createPrimarySuffix(
                            tool.createNodeDot(),
                            tool.createName("length"))
                )
        );
        equalityExpression.add(
                tool.createNode(new ASTEqualityTokens(EcmaParserTreeConstants.JJTEQUALITYTOKENS),
                        EcmaParserConstants.EQ,
                        "==")
        );

        equalityExpression.add(tool.createPrimaryExpression(
                tool.createPrimaryPrefix(tool.createNode(
                        new ASTLiteral(EcmaParserTreeConstants.JJTLITERAL),
                        EcmaParserConstants.INTEGER_LITERAL,
                        String.valueOf(cntFormalParams)
                ))
        ));

        ifStatement.add(equalityExpression);

        ifStatement.add(tool.createNode(new ASTRParenToken(EcmaParserTreeConstants.JJTRPARENTOKEN),
                EcmaParserConstants.RPAREN,")"));



        ASTStatement branchStatement=new ASTStatement(EcmaParserTreeConstants.JJTSTATEMENT);
        branchStatement.add(
                tool.createNode(new ASTLBraceToken(EcmaParserTreeConstants.JJTLBRACETOKEN),
                        EcmaParserConstants.LBRACE,"{")
        );
        ifStatement.add(branchStatement);

        ASTStatement statement=new ASTStatement(EcmaParserTreeConstants.JJTSTATEMENT);
        statement.add(ifStatement);
        blockStatement.add(statement);

        // arguments
        ccbus.tool.parser.java.ASTArguments arguments=
                new ccbus.tool.parser.java.ASTArguments(JavaParserTreeConstants.JJTARGUMENTS);
        ccbus.tool.parser.java.ASTArgumentList argumentList=
                new ccbus.tool.parser.java.ASTArgumentList(JavaParserTreeConstants.JJTARGUMENTLIST);
        arguments.add(tool.createNodeLParen());
        arguments.add(argumentList);

        ccbus.tool.parser.java.ASTExpression argumentExpression=
                new ccbus.tool.parser.java.ASTExpression(JavaParserTreeConstants.JJTEXPRESSION);
        argumentExpression.add(tool.createName("this"));
        argumentExpression.add(tool.createNodeComma());
        argumentExpression.add(tool.createName("arguments"));

        argumentList.add(argumentExpression);
        arguments.add(tool.createNodeRParen());

        // this token
        ccbus.tool.parser.java.ASTThisToken thisToken=new ccbus.tool.parser.java.ASTThisToken(JavaParserTreeConstants.JJTTHISTOKEN);
        ccbus.tool.parser.java.Token tokenIdentifier= ccbus.tool.parser.java.Token.newToken(
                JavaParserConstants.THIS, "this");
        thisToken.jjtSetFirstToken(tokenIdentifier);
        thisToken.jjtSetLastToken(tokenIdentifier);


        ccbus.tool.parser.java.ASTPrimaryExpression primaryExpression=tool.createPrimaryExpression(
                tool.createPrimaryPrefix(thisToken),
                tool.createPrimarySuffix(
                        tool.createNodeDot(),
                        tool.createIdentifier(identifierCtr.jjtGetFirstToken().image),
                        tool.createNodeDot(),
                        tool.createIdentifier("apply")),
                tool.createPrimarySuffix(arguments)
        );

        ASTPrimaryExpression primaryExpressionResult=(ASTPrimaryExpression)
                (new PrimaryExpression()).translate(primaryExpression,tree);

        branchStatement.add(primaryExpressionResult);
        branchStatement.add(
                tool.createNode(new ASTLBraceToken(EcmaParserTreeConstants.JJTRBRACETOKEN),
                        EcmaParserConstants.RBRACE,"}")
        );

    }
}
