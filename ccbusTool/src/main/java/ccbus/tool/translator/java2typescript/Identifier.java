package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.ASTIdentifier;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.TranslatedTree;

public class Identifier  implements TreeTranslator
{
    public Node translate(Node node,TranslatedTree tree)
    {
        ASTIdentifier nodeResult=new ASTIdentifier(AngularParserTreeConstants.JJTIDENTIFIER);
        nodeResult.jjtSetFirstToken(node.jjtGetFirstToken());
        tree.add(nodeResult);

        return nodeResult;
    }
}

