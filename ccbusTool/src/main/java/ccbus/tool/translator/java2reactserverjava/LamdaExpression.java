package ccbus.tool.translator.java2reactserverjava;

import ccbus.tool.intermediate.Node;
import ccbus.tool.translator.RecursiveHostTranslator;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.util.java.Tool;

public class LamdaExpression extends GenericTreeTranslator implements TreeTranslator,RecursiveHostTranslator
{
    public Node translate(ccbus.tool.intermediate.Node node, TranslatedTree tree)
    {
        ((Tool)tree.tool()).symTabStack().push(node.symTab());
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            tree.translateRecursive(node.jjtGetChild(i), this);
        }
        ((Tool)tree.tool()).symTabStack().pop();
        return null;
    }
}
