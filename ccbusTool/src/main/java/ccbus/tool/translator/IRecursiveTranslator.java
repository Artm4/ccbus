package ccbus.tool.translator;

import ccbus.tool.intermediate.Node;
import ccbus.tool.util.Pair;

public interface IRecursiveTranslator {
    public int getToId(int fromId);
    public Node translate(Node node, TranslatedTree tree, RecursiveHostTranslator hostTranslator);
    public Pair<Boolean,Node> translateNode(Node node, TranslatedTree tree, IRecursiveTranslator recursiveTranslator);
}
