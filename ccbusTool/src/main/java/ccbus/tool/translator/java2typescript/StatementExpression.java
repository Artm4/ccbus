package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.intermediate.SimpleNode;
import ccbus.tool.parser.typescript.ASTStatementExpression;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;
import ccbus.tool.parser.java.JavaParserTreeConstants;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.TranslatedTree;

public class StatementExpression implements TreeTranslator {
    public Node translate(Node node, TranslatedTree tree)
    {
        ASTStatementExpression nodeResult=
                new ASTStatementExpression(AngularParserTreeConstants.JJTSTATEMENTEXPRESSION);
        tree.add(nodeResult);


        SimpleNode expressionResult=
                    (SimpleNode)
                    tree.translate(JavaParserTreeConstants.JJTEXPRESSION,node);
            nodeResult.add(expressionResult);

        return expressionResult;
    }
}
