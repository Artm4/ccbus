package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.SimpleNode;
import ccbus.tool.parser.ecmascript.Token;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;

public class SingleToken implements TreeTranslator
{
    public Node translate(Node node , TranslatedTree tree, int angularId)
    {
        SimpleNode nodeResult=new SimpleNode(angularId);
        tree.add(nodeResult);
        Token token=Token.newToken(angularId,node.jjtGetFirstToken().image);
        nodeResult.jjtSetFirstToken(token);
        return nodeResult;
    }

    public Node translate(Node node, TranslatedTree tree) {
        return null;
    }
}
