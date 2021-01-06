package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.ASTMethodDeclaration;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.parser.java.*;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.util.java.Tool;

public class MethodDeclaration implements TreeTranslator
{
    public Node translate(Node node,TranslatedTree tree)
    {
        ((Tool)tree.tool()).symTabStack().push(node.symTab());
        ASTMethodDeclaration nodeResult=
                new ASTMethodDeclaration(EcmaParserTreeConstants.JJTMETHODDECLARATION);
        tree.add(nodeResult);

        ASTMethodDeclarator nodeMethodDeclarator=
                (ASTMethodDeclarator)
            node.findFirstDownById(JavaParserTreeConstants.JJTMETHODDECLARATOR,1);
        ASTBlock nodeBlock=(ASTBlock)node.findFirstDownById(JavaParserTreeConstants.JJTBLOCK,1);
        ASTSemicolonToken nodeSemicolon=(ASTSemicolonToken)node.findFirstDownById(JavaParserTreeConstants.JJTSEMICOLONTOKEN,1);
        ASTResultType nodeResultType=(ASTResultType) node.findFirstDownById(JavaParserTreeConstants.JJTRESULTTYPE,1);
        ASTBlockConstructor nodeBlockConstructor=(ASTBlockConstructor)node.findFirstDownById(JavaParserTreeConstants.JJTBLOCKCONSTRUCTOR,1);

        if(null!=nodeBlock)
        {
            ccbus.tool.parser.ecmascript.ASTBlock nodeBlockResult=
                    (ccbus.tool.parser.ecmascript.ASTBlock)
                            tree.translate(JavaParserTreeConstants.JJTBLOCK,nodeBlock);
            nodeResult.add(nodeBlockResult);
        }

        if(null!=nodeBlockConstructor)
        {
            ccbus.tool.parser.ecmascript.ASTBlock nodeBlockResult=
                    (ccbus.tool.parser.ecmascript.ASTBlock)
                            tree.translate(JavaParserTreeConstants.JJTBLOCK,nodeBlockConstructor);
            nodeResult.add(nodeBlockResult);
        }


        if(null!=nodeSemicolon)
        {
            ccbus.tool.parser.ecmascript.SimpleNode nodeSemicolonResult=
                    (ccbus.tool.parser.ecmascript.SimpleNode )
                    tree.translateSingleToken(nodeSemicolon,EcmaParserTreeConstants.JJTSEMICOLONTOKEN);
            nodeResult.add(nodeSemicolonResult);
        }

        if(null!=nodeResultType)
        {
            ccbus.tool.parser.ecmascript.ASTResultType nodeResultTypeResult=
                    (ccbus.tool.parser.ecmascript.ASTResultType )
                            tree.translate(JavaParserTreeConstants.JJTRESULTTYPE,nodeResultType);
            nodeResult.add(nodeResultTypeResult);
        }

        if(null!=nodeMethodDeclarator)
        {
            ccbus.tool.parser.ecmascript.ASTMethodDeclarator nodeMethodDeclaratorResult=
                    (ccbus.tool.parser.ecmascript.ASTMethodDeclarator )
                            tree.translate(JavaParserTreeConstants.JJTMETHODDECLARATOR,nodeMethodDeclarator);
            nodeResult.add(nodeMethodDeclaratorResult);
        }
        ((Tool)tree.tool()).symTabStack().pop();
        return nodeResult;
    }
}
