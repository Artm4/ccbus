package ccbus.tool.translator.java2reactserverjava;

import ccbus.tool.intermediate.Node;
import ccbus.tool.translator.RecursiveHostTranslator;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;

public class MethodDeclaration extends GenericTreeTranslator implements TreeTranslator,RecursiveHostTranslator
{
    public Node translate(Node node, TranslatedTree tree)
    {
        for(int i=0;i<node.jjtGetNumChildren();i++)
        {
            tree.translateRecursive(node.jjtGetChild(i),this);
        }
        return null;
    }

}
