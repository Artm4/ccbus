package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.intermediate.SimpleNode;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;
import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.RecursiveHostTranslator;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.TranslatedTree;


public class Expression extends GenericTreeTranslator implements TreeTranslator ,RecursiveHostTranslator
{

    public Node translate(Node node, TranslatedTree tree)
    {
        ccbus.tool.parser.typescript.ASTExpression nodeResult=
                new ccbus.tool.parser.typescript.ASTExpression(AngularParserTreeConstants.JJTEXPRESSION);
        tree.add(nodeResult);

        SimpleNode conditionalExpression=
                (SimpleNode) node.findFirstDownById(JavaParserTreeConstants.JJTCONDITIONALEXPRESSION,1);
        if(null!=conditionalExpression)
        {
            SimpleNode conditionalExpressionResult =
                    (SimpleNode) tree.translateRecursive(conditionalExpression, this);
            nodeResult.add(conditionalExpressionResult);
        }

        SimpleNode assignmentOperator=
                (SimpleNode) node.findFirstDownById(JavaParserTreeConstants.JJTASSIGNMENTOPERATOR,1);
        if(null!=assignmentOperator)
        {
            SimpleNode assignmentOperatorResult =
                    (SimpleNode) tree.translateRecursive(assignmentOperator, this);
            nodeResult.add(assignmentOperatorResult);
        }

        SimpleNode expression=
                (SimpleNode) node.findFirstDownById(JavaParserTreeConstants.JJTEXPRESSION,1);
        if(null!=expression)
        {
            ccbus.tool.parser.typescript.ASTExpression expressionResult =
                    (ccbus.tool.parser.typescript.ASTExpression)
                            tree.translate(JavaParserTreeConstants.JJTEXPRESSION,expression);
            nodeResult.add(expressionResult);
        }

        return nodeResult;
    }

}
