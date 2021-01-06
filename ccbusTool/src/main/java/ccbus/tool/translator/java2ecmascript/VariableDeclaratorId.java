package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.ASTIdentifier;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.parser.java.ASTVariableDeclaratorId;
import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;

public class VariableDeclaratorId implements TreeTranslator
{
    public Node translate(Node node,TranslatedTree tree)
    {
        return translate((ASTVariableDeclaratorId)node,tree);
    }

    public Node translate(ASTVariableDeclaratorId node,TranslatedTree tree)
    {
        ccbus.tool.parser.ecmascript.ASTVariableDeclaratorId nodeResult=
                new ccbus.tool.parser.ecmascript
                        .ASTVariableDeclaratorId(EcmaParserTreeConstants.JJTVARIABLEDECLARATORID);
        tree.add(nodeResult);

        ASTIdentifier identifierResult=(ASTIdentifier) tree.translate(
                JavaParserTreeConstants.JJTIDENTIFIER,
                node.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,1)
                );

        nodeResult.add(identifierResult);

        return nodeResult;
    }
}
