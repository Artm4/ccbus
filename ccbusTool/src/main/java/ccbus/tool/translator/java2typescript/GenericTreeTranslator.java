package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.IRecursiveTranslator;
import ccbus.tool.translator.RecursiveHostTranslator;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.util.Pair;
import ccbus.tool.translator.TranslatedTree;

public class GenericTreeTranslator implements TreeTranslator ,RecursiveHostTranslator {


    public Node translate(Node node, TranslatedTree tree)
    {
        Node nodeResult=tree.translateRecursive(node,this);
        return nodeResult;
    }


    public Pair<Boolean, Node> translateNode(Node node, TranslatedTree tree, IRecursiveTranslator recursiveTranslator)
    {
        Pair<Boolean,Node> result;
        switch (node.getId())
        {
            case JavaParserTreeConstants.JJTEXPRESSION:
                result=new Pair<Boolean, Node>(false,tree.translate(JavaParserTreeConstants.JJTEXPRESSION,node));
                break;
            case JavaParserTreeConstants.JJTBLOCK:
                result=new Pair<Boolean, Node>(false,tree.translate(JavaParserTreeConstants.JJTBLOCK,node));
                break;
            case JavaParserTreeConstants.JJTFORMALPARAMETER:
                result=new Pair<Boolean, Node>(false,tree.translate(JavaParserTreeConstants.JJTFORMALPARAMETER,node));
                break;
            case JavaParserTreeConstants.JJTBLOCKSTATEMENT:
                result=new Pair<Boolean, Node>(false,tree.translate(JavaParserTreeConstants.JJTBLOCKSTATEMENT,node));
                break;
            case JavaParserTreeConstants.JJTLOCALVARIABLEDECLARATION:
                result=new Pair<Boolean, Node>(false,tree.translate(JavaParserTreeConstants.JJTLOCALVARIABLEDECLARATION,node));
                break;
            case JavaParserTreeConstants.JJTFORSTATEMENTRANGE:
                result=new Pair<Boolean, Node>(false,tree.translate(JavaParserTreeConstants.JJTFORSTATEMENTRANGE,node));
                break;
//            case JavaParserTreeConstants.JJTVARIABLEINITIALIZER:
//                result=new Pair<Boolean, Node>(false,tree.translate(JavaParserTreeConstants.JJTVARIABLEINITIALIZER,node));
//                break;
            default:
                result=recursiveTranslator.translateNode(node,tree,recursiveTranslator);
        }

        return result;
    }
}
