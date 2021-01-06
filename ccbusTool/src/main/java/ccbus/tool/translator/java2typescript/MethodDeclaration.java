package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.ASTMethodDeclaration;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;
import ccbus.tool.parser.java.*;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.TranslatedTree;

public class MethodDeclaration implements TreeTranslator
{
    public Node translate(Node node,TranslatedTree tree)
    {
        ASTMethodDeclaration nodeResult=
                new ASTMethodDeclaration(AngularParserTreeConstants.JJTMETHODDECLARATION);
        tree.add(nodeResult);

        ccbus.tool.parser.java.ASTMethodDeclarator nodeMethodDeclarator=
                (ccbus.tool.parser.java.ASTMethodDeclarator)
            node.findFirstDownById(JavaParserTreeConstants.JJTMETHODDECLARATOR,1);
        ASTBlock nodeBlock=(ASTBlock)node.findFirstDownById(JavaParserTreeConstants.JJTBLOCK,1);
        ASTSemicolonToken nodeSemicolon=(ASTSemicolonToken)node.findFirstDownById(JavaParserTreeConstants.JJTSEMICOLONTOKEN,1);
        ASTResultType nodeResultType=(ASTResultType) node.findFirstDownById(JavaParserTreeConstants.JJTRESULTTYPE,1);

        if(null!=nodeBlock)
        {
            ccbus.tool.parser.typescript.ASTBlock nodeBlockResult=
                    (ccbus.tool.parser.typescript.ASTBlock)
                            tree.translate(JavaParserTreeConstants.JJTBLOCK,nodeBlock);
            nodeResult.add(nodeBlockResult);
        }

        if(null!=nodeSemicolon)
        {
            ccbus.tool.parser.typescript.SimpleNode nodeSemicolonResult=
                    (ccbus.tool.parser.typescript.SimpleNode )
                    tree.translateSingleToken(nodeSemicolon,AngularParserTreeConstants.JJTSEMICOLONTOKEN);
            nodeResult.add(nodeSemicolonResult);
        }

        if(null!=nodeResultType)
        {
            ccbus.tool.parser.typescript.ASTResultType nodeResultTypeResult=
                    (ccbus.tool.parser.typescript.ASTResultType )
                            tree.translate(JavaParserTreeConstants.JJTRESULTTYPE,nodeResultType);
            nodeResult.add(nodeResultTypeResult);
        }

        if(null!=nodeMethodDeclarator)
        {
            ccbus.tool.parser.typescript.ASTMethodDeclarator nodeMethodDeclaratorResult=
                    (ccbus.tool.parser.typescript.ASTMethodDeclarator )
                            tree.translate(JavaParserTreeConstants.JJTMETHODDECLARATOR,nodeMethodDeclarator);
            nodeResult.add(nodeMethodDeclaratorResult);
        }
        return nodeResult;
    }
}
