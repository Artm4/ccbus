package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.ASTBlock;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.parser.java.ASTBlockStatement;
import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.RecursiveHostTranslator;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;

public class Block extends GenericTreeTranslator implements TreeTranslator,RecursiveHostTranslator {
    public Node translate(Node node, TranslatedTree tree)
    {
        ASTBlock nodeResult=new ASTBlock(EcmaParserTreeConstants.JJTBLOCK);
        tree.add(nodeResult);

        for(int i=0;i<node.jjtGetNumChildren();i++)
        {
            if(node.jjtGetChild(i).getId()==JavaParserTreeConstants.JJTBLOCKSTATEMENT)
            {
                ASTBlockStatement blockStatement=(ASTBlockStatement)
                        node.jjtGetChild(i);

                ccbus.tool.parser.ecmascript.ASTBlockStatement blockStatementResult=
                        (ccbus.tool.parser.ecmascript.ASTBlockStatement)
                                tree.translate(JavaParserTreeConstants.JJTBLOCKSTATEMENT,blockStatement);
                nodeResult.add(blockStatementResult);
            }
            else
            {
                ccbus.tool.parser.ecmascript.ASTBlockStatement blockStatement=
                        new ccbus.tool.parser.ecmascript.ASTBlockStatement(EcmaParserTreeConstants.JJTBLOCKSTATEMENT);

                ccbus.tool.parser.ecmascript.ASTStatement statement=
                        new ccbus.tool.parser.ecmascript.ASTStatement(EcmaParserTreeConstants.JJTSTATEMENT);

                blockStatement.add(statement);

                Node nodeChildResult=tree.translateRecursive(node.jjtGetChild(i),this);
                statement.add(nodeChildResult);

                nodeResult.add(blockStatement);
            }
        }

        return nodeResult;
    }
}
