package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;
import ccbus.tool.parser.java.ASTBracket;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.TranslatedTree;

public class Bracket implements TreeTranslator
{
    public Node translate(Node node , TranslatedTree tree)
    {
        return translate((ASTBracket)node,tree);
    }

    public Node translate(ASTBracket node,TranslatedTree tree)
    {
        ccbus.tool.parser.typescript.ASTBracket nodeResult=
                new ccbus.tool.parser.typescript.ASTBracket(AngularParserTreeConstants.JJTBRACKET);
        tree.add(nodeResult);

        nodeResult.jjtSetFirstToken(node.jjtGetFirstToken());
        //nodeResult.jjtGetFirstToken().next=node.jjtGetFirstToken().next;
        nodeResult.jjtSetLastToken(node.jjtGetLastToken());
        return nodeResult;
    }
}
