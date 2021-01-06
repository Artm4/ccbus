package ccbus.tool.translator;

import ccbus.tool.intermediate.Node;
import ccbus.tool.intermediate.SimpleNode;
import ccbus.tool.util.Pair;


public class RecursiveTranslatorImpl implements IRecursiveTranslator,TreeTranslator
{
    public Node translate(Node node, TranslatedTree tree,RecursiveHostTranslator hostTranslator)
    {
        Pair<Boolean,Node> result=hostTranslator.translateNode(node,tree,this);
        SimpleNode nodeResult=(SimpleNode)result.getValue();

        if(false==result.getKey()){return nodeResult;}
        tree.add(nodeResult);

        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            Node child = tree.translateRecursive(node.jjtGetChild(i), hostTranslator);
            nodeResult.add(child);
        }
        return nodeResult;
    }

    public Node translate(Node node, TranslatedTree tree) {
        return null;
    }

    public int getToId(int fromId) {
        return 0;
    }

    public Pair<Boolean,Node> translateNode(Node node, TranslatedTree tree, IRecursiveTranslator recursiveTranslator) {
        SimpleNode nodeResult = (SimpleNode) node.createCopy(recursiveTranslator.getToId(node.getId()));
        return new Pair<Boolean,Node>(true,nodeResult);
    }
}