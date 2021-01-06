package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;

import ccbus.tool.parser.typescript.AngularParserTreeConstants;

import ccbus.tool.translator.RecursiveHostTranslator;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.TranslatedTree;

public class Statement extends GenericTreeTranslator implements TreeTranslator,RecursiveHostTranslator {
    public Node translate(Node node, TranslatedTree tree)
    {
        Node nodeResult=tree.translateRecursive(node,this);
        nodeResult.setId(AngularParserTreeConstants.JJTSTATEMENT);
        return nodeResult;
    }
}
