package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.intermediate.Token;
import ccbus.tool.parser.ecmascript.ASTPrimarySuffix;
import ccbus.tool.parser.ecmascript.EcmaParserConstants;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.parser.ecmascript.ASTDotToken;

import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;

public class MethodReference implements TreeTranslator
{
    public Node translate(Node node, TranslatedTree tree)
    {
        ASTPrimarySuffix nodeResult=
                new ASTPrimarySuffix(EcmaParserTreeConstants.JJTMETHODDECLARATION);
        tree.add(nodeResult);

        ASTDotToken dotNode=new ASTDotToken(EcmaParserTreeConstants.JJTDOTOKEN);
        Token dotToken= ccbus.tool.parser.ecmascript.Token.newToken(
                EcmaParserConstants.DOT, ".");
        dotNode.jjtSetFirstToken(dotToken);
        dotNode.jjtSetLastToken(dotToken);

        nodeResult.add(dotNode);
        nodeResult.add(node.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,1));

        return nodeResult;
    }
}
