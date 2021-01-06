package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.ASTIdentifier;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;

public class Identifier  implements TreeTranslator
{
    public Node translate(Node node,TranslatedTree tree)
    {
        ASTIdentifier nodeResult=new ASTIdentifier(EcmaParserTreeConstants.JJTIDENTIFIER);
        nodeResult.jjtSetFirstToken(node.jjtGetFirstToken());
        tree.add(nodeResult);

        return nodeResult;
    }
}

