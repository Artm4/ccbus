package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.IRecursiveTranslator;
import ccbus.tool.translator.RecursiveHostTranslator;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.util.java.Tool;
import ccbus.tool.util.Pair;

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
            case JavaParserTreeConstants.JJTCASTEXPRESSION:
                result=new Pair<Boolean, Node>(false,tree.translate(JavaParserTreeConstants.JJTCASTEXPRESSION,node));
                break;
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
            case JavaParserTreeConstants.JJTCLASSORINTERFACETYPE:
                result=new Pair<Boolean, Node>(false,tree.translate(JavaParserTreeConstants.JJTCLASSORINTERFACETYPE,node));
                break;
            case JavaParserTreeConstants.JJTALLOCATIONEXPRESSION:
                result=new Pair<Boolean, Node>(false,tree.translate(JavaParserTreeConstants.JJTALLOCATIONEXPRESSION,node));
                break;
            case JavaParserTreeConstants.JJTMETHODREFERENCE:
                result=new Pair<Boolean, Node>(false,tree.translate(JavaParserTreeConstants.JJTMETHODREFERENCE,node));
                break;
            case JavaParserTreeConstants.JJTPRIMARYEXPRESSION:
                result=new Pair<Boolean, Node>(false,tree.translate(JavaParserTreeConstants.JJTPRIMARYEXPRESSION,node));
                break;
            case JavaParserTreeConstants.JJTLAMDAEXPRESSION:
                result=new Pair<Boolean, Node>(false,tree.translate(JavaParserTreeConstants.JJTLAMDAEXPRESSION,node));
                break;
            default:
                result=recursiveTranslator.translateNode(node,tree,recursiveTranslator);
        }

        return result;
    }

}
