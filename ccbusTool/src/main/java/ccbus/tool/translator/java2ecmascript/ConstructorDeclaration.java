package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.ASTMethodDeclaration;
import ccbus.tool.parser.ecmascript.ASTIdentifier;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.parser.java.*;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.util.java.Tool;

public class ConstructorDeclaration extends MethodDeclaration
{
    public Node translate(Node node,TranslatedTree tree)
    {
        Tool tool=(Tool) tree.tool();
        ASTMethodDeclaration nodeResult=
                (ASTMethodDeclaration)super.translate(node,tree);

        ASTIdentifier methodIdentifier=(ASTIdentifier)
                nodeResult.findFirstDownById(EcmaParserTreeConstants.JJTIDENTIFIER,2);
        tool.createToken(EcmaParserTreeConstants.JJTIDENTIFIER,"constructor",methodIdentifier);

        return nodeResult;
    }
}
