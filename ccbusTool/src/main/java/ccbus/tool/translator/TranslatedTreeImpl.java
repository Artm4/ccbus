package ccbus.tool.translator;

import ccbus.tool.intermediate.Node;
import ccbus.tool.util.Tool;

public abstract class TranslatedTreeImpl {
    protected Node currentNode;
    protected Node parentNode;
    protected TranslatedNode translatedNode;
    protected Tool tool;

    public Node currentNode() {
        return currentNode;
    }

    public Node parentNode() {
        return parentNode;
    }

    public Node translatedNode()
    {
        return translatedNode;
    }

    public Tool tool() {
        return tool;
    }

    public void add(Node node)
    {
        parentNode=currentNode;
        currentNode=node;

        TranslatedNode last=new TranslatedNode(node);
        translatedNode.add(last);

        translatedNode=last;
    }

    public abstract Node translate(int translatorId,Node node);

    public abstract Node translateSingleToken(Node node,int toId);

    public abstract Node translateRecursive(Node node,RecursiveHostTranslator hostTranslator);

    public TranslatedTreeImpl(Node curNode, TranslatedNode trNode)
    {
        this.translatedNode=trNode;
        this.currentNode=curNode;
    }

    public TranslatedTreeImpl(Node curNode, TranslatedNode trNode, Tool tool)
    {
        this.translatedNode=trNode;
        this.currentNode=curNode;
        this.tool=tool;
    }

    public TranslatedTreeImpl(Node curNode)
    {
        this(curNode,new TranslatedNode(curNode),null);
    }

    public TranslatedTreeImpl(Node curNode,Tool tool)
    {
        this(curNode,new TranslatedNode(curNode),tool);
    }

    protected abstract ccbus.tool.translator.TranslatedTree create();
}
