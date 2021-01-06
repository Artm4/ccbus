package ccbus.tool.compiler.generator.ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.ASTBlockStatement;
import ccbus.tool.parser.ecmascript.EcmaParserConstants;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;

public class Block  extends CodeGenerator {

    public Block(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        this.emitLn();
        this.emitKind(EcmaParserConstants.LBRACE);
        this.indentAdd();
        ASTBlockStatement blockStatement=(ASTBlockStatement)
                node.findNextDownById(EcmaParserTreeConstants.JJTBLOCKSTATEMENT,1);
        while(null!=blockStatement)
        {
            (new BlockStatement(this)).generate(blockStatement);
            blockStatement=(ASTBlockStatement)
                    node.findNextDownById(EcmaParserTreeConstants.JJTBLOCKSTATEMENT,1);
        }
        this.indentRemove();
        this.emitLn();
        this.emitKind(EcmaParserConstants.RBRACE);
    }
}
