package ccbus.tool.translator;

import ccbus.tool.intermediate.Node;
import ccbus.tool.util.Tool;

public interface TranslatedTree {

    public Node currentNode();

    public Node parentNode();

    public Node translatedNode();

    public void add(Node node);

    public Node translate(int translatorId,Node node);

    public Node translateSingleToken(Node node,int toId);

    public Node translateRecursive(Node node,RecursiveHostTranslator hostTranslator);

    public Tool tool();
}
