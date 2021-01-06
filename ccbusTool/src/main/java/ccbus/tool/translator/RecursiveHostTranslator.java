package ccbus.tool.translator;

import ccbus.tool.intermediate.Node;
import ccbus.tool.util.Pair;

public interface RecursiveHostTranslator {
    public Pair<Boolean,Node> translateNode(Node node, TranslatedTree tree, IRecursiveTranslator recursiveTranslator);
}
