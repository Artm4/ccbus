package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.ASTBlock;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;
import ccbus.tool.parser.java.ASTBlockStatement;
import ccbus.tool.parser.java.JavaParserTreeConstants;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.TranslatedTree;

public class Block implements TreeTranslator {
    public Node translate(Node node, TranslatedTree tree)
    {
        ASTBlock nodeResult=new ASTBlock(AngularParserTreeConstants.JJTBLOCK);
        tree.add(nodeResult);

        ASTBlockStatement blockStatement=(ASTBlockStatement)
                node.findNextDownById(JavaParserTreeConstants.JJTBLOCKSTATEMENT,1);
        for(int i=0;null!=blockStatement;i++)
        {
            ccbus.tool.parser.typescript.ASTBlockStatement blockStatementResult=
                    (ccbus.tool.parser.typescript.ASTBlockStatement)
                    tree.translate(JavaParserTreeConstants.JJTBLOCKSTATEMENT,blockStatement);
            nodeResult.add(blockStatementResult);

            blockStatement=(ASTBlockStatement)
                    node.findNextDownById(JavaParserTreeConstants.JJTBLOCKSTATEMENT,1);

        }

        return nodeResult;
    }
}
