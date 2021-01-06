package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.parser.java.ASTBracket;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;

public class Bracket implements TreeTranslator
{
    public Node translate(Node node , TranslatedTree tree)
    {
        return translate((ASTBracket)node,tree);
    }

    public Node translate(ASTBracket node,TranslatedTree tree)
    {
        ccbus.tool.parser.ecmascript.ASTBracket nodeResult=
                new ccbus.tool.parser.ecmascript.ASTBracket(EcmaParserTreeConstants.JJTBRACKET);
        tree.add(nodeResult);

        nodeResult.jjtSetFirstToken(node.jjtGetFirstToken());
        //nodeResult.jjtGetFirstToken().next=node.jjtGetFirstToken().next;
        nodeResult.jjtSetLastToken(node.jjtGetLastToken());
        return nodeResult;
    }
}
