package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.translator.RecursiveHostTranslator;
import ccbus.tool.translator.TranslatedNode;
import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.util.java.Tool;


public class TranslatedTree extends ccbus.tool.translator.TranslatedTreeImpl implements ccbus.tool.translator.TranslatedTree {

    public TranslatedTree(Node curNode, TranslatedNode trNode,Tool tool) {
        super(curNode, trNode,tool);
    }

    public TranslatedTree(Node curNode,Tool tool) {
        super(curNode,tool);
    }

    public TranslatedTree(Node curNode) {
        super(curNode);
    }

    public Node translate(int translatorId, Node node)
    {
        TreeTranslator translator=TranslatorTable.getTranslator(translatorId);
        return translator.translate(node,create());
    }

    public Node translateSingleToken(Node node,int toId)
    {
        return (TranslatorTable.getSingleToken()).translate(node,create(),toId);
    }

    public Node translateRecursive(Node node,RecursiveHostTranslator hostTranslator)
    {
        return (TranslatorTable.getRecursiveTranslator()).translate(node,create(),hostTranslator);
    }

    protected ccbus.tool.translator.TranslatedTree create()
    {
        return new TranslatedTree(this.currentNode,this.translatedNode,(Tool)this.tool);
    }

    public Tool tool() {
        return (Tool)tool;
    }
}
