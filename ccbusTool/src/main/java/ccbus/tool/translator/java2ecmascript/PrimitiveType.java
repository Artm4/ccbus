package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.EcmaParserConstants;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.parser.ecmascript.Token;
import ccbus.tool.parser.java.ASTPrimitiveType;
import ccbus.tool.parser.java.JavaParserConstants;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;

public class PrimitiveType implements TreeTranslator
{
    public Node translate(Node node,TranslatedTree tree)
    {
        return translate((ASTPrimitiveType) node,tree);
    }

    public Node translate(ASTPrimitiveType node,TranslatedTree tree)
    {
        int tokenKind=EcmaParserConstants.ANY;
        switch(node.jjtGetFirstToken().kind)
        {
            case JavaParserConstants.BOOLEAN:
                tokenKind=EcmaParserConstants.BOOLEAN;
                break;
            case JavaParserConstants.CHAR:
                tokenKind=EcmaParserConstants.SYMBOL;
                break;
            case JavaParserConstants.BYTE:
            case JavaParserConstants.SHORT:
            case JavaParserConstants.INT:
            case JavaParserConstants.LONG:
            case JavaParserConstants.FLOAT:
            case JavaParserConstants.DOUBLE:
                tokenKind=EcmaParserConstants.NUMBER;
                break;
        }
        Token token=Token.newTokenInit(tokenKind);
        ccbus.tool.parser.ecmascript.ASTPrimitiveType nodeResult=
                new ccbus.tool.parser.ecmascript.ASTPrimitiveType(EcmaParserTreeConstants.JJTPRIMITIVETYPE);
        nodeResult.jjtSetFirstToken(token);
        tree.add(nodeResult);
        return nodeResult;
    }

}
