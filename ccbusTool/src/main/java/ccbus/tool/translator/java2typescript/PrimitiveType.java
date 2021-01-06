package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.AngularParserConstants;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;
import ccbus.tool.parser.typescript.Token;
import ccbus.tool.parser.java.ASTPrimitiveType;
import ccbus.tool.parser.java.JavaParserConstants;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.TranslatedTree;

public class PrimitiveType implements TreeTranslator
{
    public Node translate(Node node,TranslatedTree tree)
    {
        return translate((ASTPrimitiveType) node,tree);
    }

    public Node translate(ASTPrimitiveType node,TranslatedTree tree)
    {
        int tokenKind=AngularParserConstants.ANY;
        switch(node.jjtGetFirstToken().kind)
        {
            case JavaParserConstants.BOOLEAN:
                tokenKind=AngularParserConstants.BOOLEAN;
                break;
            case JavaParserConstants.CHAR:
                tokenKind=AngularParserConstants.SYMBOL;
                break;
            case JavaParserConstants.BYTE:
            case JavaParserConstants.SHORT:
            case JavaParserConstants.INT:
            case JavaParserConstants.LONG:
            case JavaParserConstants.FLOAT:
            case JavaParserConstants.DOUBLE:
                tokenKind=AngularParserConstants.NUMBER;
                break;
        }
        Token token=Token.newTokenInit(tokenKind);
        ccbus.tool.parser.typescript.ASTPrimitiveType nodeResult=
                new ccbus.tool.parser.typescript.ASTPrimitiveType(AngularParserTreeConstants.JJTPRIMITIVETYPE);
        nodeResult.jjtSetFirstToken(token);
        tree.add(nodeResult);
        return nodeResult;
    }

}
