package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.ASTCastExpression;
import ccbus.tool.parser.ecmascript.EcmaParserConstants;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.RecursiveHostTranslator;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;

import java.util.Arrays;
import java.util.List;

public class CastExpression  extends GenericTreeTranslator implements TreeTranslator ,RecursiveHostTranslator
{
    public static List exclude=
            Arrays.asList(
                JavaParserTreeConstants.JJTTYPE,
                JavaParserTreeConstants.JJTLPARENTOKEN,
                JavaParserTreeConstants.JJTRPARENTOKEN);
    public Node translate(Node node , TranslatedTree tree)
    {
        ASTCastExpression result=new ASTCastExpression(EcmaParserTreeConstants.JJTCASTEXPRESSION);
        for(int i=0;i<node.jjtGetNumChildren();i++)
        {

            if(!exclude.contains(node.jjtGetChild(i).getId()))
            {
                result.add(tree.translateRecursive(node.jjtGetChild(i),this));
            }
        }
        return result;
    }
}