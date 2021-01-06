package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.translator.RecursiveHostTranslator;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;

public class Statement extends GenericTreeTranslator implements TreeTranslator,RecursiveHostTranslator {
    public Node translate(Node node, TranslatedTree tree)
    {
        Node nodeResult=tree.translateRecursive(node,this);
        nodeResult.setId(EcmaParserTreeConstants.JJTSTATEMENT);
        return nodeResult;
    }
}
