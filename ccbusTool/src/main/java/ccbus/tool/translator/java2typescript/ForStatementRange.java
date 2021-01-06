package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;
import ccbus.tool.parser.typescript.ASTForStatementRange;

import ccbus.tool.parser.java.ASTExpression;
import ccbus.tool.parser.java.ASTIdentifier;
import ccbus.tool.parser.java.ASTType;
import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.RecursiveHostTranslator;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.TranslatedTree;

public class ForStatementRange extends GenericTreeTranslator implements TreeTranslator,RecursiveHostTranslator {
    public Node translate(Node node, TranslatedTree tree)
    {
        ccbus.tool.parser.typescript.ASTForStatementRange nodeResult=new ASTForStatementRange(AngularParserTreeConstants.JJTFORSTATEMENTRANGE);
        tree.add(nodeResult);

        ASTIdentifier identifier=(ASTIdentifier)
                node.findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER,1);
        nodeResult.add(tree.translate(JavaParserTreeConstants.JJTIDENTIFIER,identifier));

        ASTType type=(ASTType)
                node.findNextDownById(JavaParserTreeConstants.JJTTYPE,1);
        nodeResult.add(tree.translate(JavaParserTreeConstants.JJTTYPE,type));


        ASTExpression expression=(ASTExpression)
                node.findNextDownById(JavaParserTreeConstants.JJTEXPRESSION,1);
        nodeResult.add(tree.translate(JavaParserTreeConstants.JJTEXPRESSION,expression));

        return nodeResult;
    }
}
