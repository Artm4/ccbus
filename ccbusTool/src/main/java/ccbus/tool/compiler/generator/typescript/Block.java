package ccbus.tool.compiler.generator.typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.ASTBlockStatement;
import ccbus.tool.parser.typescript.AngularParserConstants;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;

public class Block  extends CodeGenerator {

    public Block(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        this.emitLn();
        this.emitKind(AngularParserConstants.LBRACE);
        this.indentAdd();
        ASTBlockStatement blockStatement=(ASTBlockStatement)
                node.findNextDownById(AngularParserTreeConstants.JJTBLOCKSTATEMENT,1);
        while(null!=blockStatement)
        {
            (new BlockStatement(this)).generate(blockStatement);
            blockStatement=(ASTBlockStatement)
                    node.findNextDownById(AngularParserTreeConstants.JJTBLOCKSTATEMENT,1);
        }
        this.indentRemove();
        this.emitLn();
        this.emitKind(AngularParserConstants.RBRACE);
    }
}
