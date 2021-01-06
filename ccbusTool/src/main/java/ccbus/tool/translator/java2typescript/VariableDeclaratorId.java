package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.ASTIdentifier;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;
import ccbus.tool.parser.java.ASTVariableDeclaratorId;
import ccbus.tool.parser.java.JavaParserTreeConstants;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.TranslatedTree;

public class VariableDeclaratorId implements TreeTranslator
{
    public Node translate(Node node,TranslatedTree tree)
    {
        return translate((ASTVariableDeclaratorId)node,tree);
    }

    public Node translate(ASTVariableDeclaratorId node,TranslatedTree tree)
    {
        ccbus.tool.parser.typescript.ASTVariableDeclaratorId nodeResult=
                new ccbus.tool.parser.typescript
                        .ASTVariableDeclaratorId(AngularParserTreeConstants.JJTVARIABLEDECLARATORID);
        tree.add(nodeResult);

        ASTIdentifier identifierResult=(ASTIdentifier) tree.translate(
                JavaParserTreeConstants.JJTIDENTIFIER,
                node.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,1)
                );

        nodeResult.add(identifierResult);

        return nodeResult;
    }
}
