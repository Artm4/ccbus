package ccbus.tool.translator.java2reactserverjava;


import ccbus.tool.intermediate.Node;
import ccbus.tool.intermediate.SimpleNode;
import ccbus.tool.translator.IRecursiveTranslator;
import ccbus.tool.translator.RecursiveHostTranslator;
import ccbus.tool.translator.RecursiveTranslatorImpl;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.util.Pair;

import java.util.HashMap;

public class RecursiveTranslator extends RecursiveTranslatorImpl
{
    private HashMap<Integer, Integer> java2angularNodeId;

    public RecursiveTranslator() {

    }

    public int getToId(int fromId) {
        return fromId;
    }

    public Node translate(Node node, TranslatedTree tree,RecursiveHostTranslator hostTranslator)
    {
        Pair<Boolean, Node> result = hostTranslator.translateNode(node, tree, this);
        SimpleNode nodeResult = (SimpleNode) result.getValue();

        if (false == result.getKey())
        {
            return nodeResult;
        }

        for (int i = 0; i < node.jjtGetNumChildren(); i++)
        {
            tree.translateRecursive(node.jjtGetChild(i), hostTranslator);
        }
        return nodeResult;
    }

    public Pair<Boolean,Node> translateNode(Node node, TranslatedTree tree, IRecursiveTranslator recursiveTranslator)
    {
        return new Pair<Boolean,Node>(true,node);
    }

}