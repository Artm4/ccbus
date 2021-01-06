package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.intermediate.SimpleNode;
import ccbus.tool.parser.ecmascript.ASTStatementExpression;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;

public class StatementExpression implements TreeTranslator {
    public Node translate(Node node, TranslatedTree tree)
    {
        ASTStatementExpression nodeResult=
                new ASTStatementExpression(EcmaParserTreeConstants.JJTSTATEMENTEXPRESSION);
        tree.add(nodeResult);


        SimpleNode expressionResult=
                    (SimpleNode)
                    tree.translate(JavaParserTreeConstants.JJTEXPRESSION,node);
            nodeResult.add(expressionResult);

        return expressionResult;
    }
}
