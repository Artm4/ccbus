package ccbus.tool.compiler.generator.typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.*;

public class BlockStatement extends CodeGenerator {

    public BlockStatement(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {

        this.emitLn();
        ASTLocalVariableDeclaration localVariableDeclaration=(ASTLocalVariableDeclaration)
                node.findFirstDownById(AngularParserTreeConstants.JJTLOCALVARIABLEDECLARATION,1);

        if(null!=localVariableDeclaration)
        {
            (new LocalVariableDeclaration(this)).generate(localVariableDeclaration);
            this.emitKind(AngularParserConstants.SEMICOLON);
        }

        ccbus.tool.intermediate.SimpleNode statement=(ccbus.tool.intermediate.SimpleNode)
                node.findFirstDownById(AngularParserTreeConstants.JJTSTATEMENT,1);
        if(null!=statement)
        {
            (new Statement(this)).generate(statement);
        }
    }
}
