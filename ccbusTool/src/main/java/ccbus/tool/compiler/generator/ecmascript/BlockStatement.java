package ccbus.tool.compiler.generator.ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.ASTLocalVariableDeclaration;
import ccbus.tool.parser.ecmascript.EcmaParserConstants;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;

public class BlockStatement extends CodeGenerator {

    public BlockStatement(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {

        this.emitLn();
        ASTLocalVariableDeclaration localVariableDeclaration=(ASTLocalVariableDeclaration)
                node.findFirstDownById(EcmaParserTreeConstants.JJTLOCALVARIABLEDECLARATION,1);

        if(null!=localVariableDeclaration)
        {
            (new LocalVariableDeclaration(this)).generate(localVariableDeclaration);
            this.emitKind(EcmaParserConstants.SEMICOLON);
        }

        ccbus.tool.intermediate.SimpleNode statement=(ccbus.tool.intermediate.SimpleNode)
                node.findFirstDownById(EcmaParserTreeConstants.JJTSTATEMENT,1);
        if(null!=statement)
        {
            (new Statement(this)).generate(statement);
        }
    }
}
