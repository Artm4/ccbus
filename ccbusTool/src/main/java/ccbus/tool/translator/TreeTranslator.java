package ccbus.tool.translator;

import ccbus.tool.intermediate.Node;

public interface TreeTranslator {
    public Node translate(Node node,TranslatedTree tree);
}
