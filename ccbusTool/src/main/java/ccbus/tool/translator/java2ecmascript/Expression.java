package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.RecursiveHostTranslator;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;

public class Expression extends GenericTreeTranslator implements TreeTranslator ,RecursiveHostTranslator
{

    public Node translate(Node node, TranslatedTree tree)
    {
        ccbus.tool.parser.ecmascript.ASTExpression nodeResult=
                new ccbus.tool.parser.ecmascript.ASTExpression(EcmaParserTreeConstants.JJTEXPRESSION);
        tree.add(nodeResult);

        for(int i=0;i<node.jjtGetNumChildren();i++)
        {
            Node currentNode=node.jjtGetChild(i);
            if(currentNode.getId()==JavaParserTreeConstants.JJTEXPRESSION)
            {
                ccbus.tool.parser.ecmascript.ASTExpression expressionResult =
                        (ccbus.tool.parser.ecmascript.ASTExpression)
                                tree.translate(JavaParserTreeConstants.JJTEXPRESSION,currentNode);
                nodeResult.add(expressionResult);
            }
            else
            {
                Node nodeChild=tree.translateRecursive(currentNode,this);
                nodeResult.add(nodeChild);
            }

        }

        return nodeResult;
    }

}
