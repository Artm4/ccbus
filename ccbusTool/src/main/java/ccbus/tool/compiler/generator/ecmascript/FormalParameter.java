package ccbus.tool.compiler.generator.ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.ASTVarParameters;
import ccbus.tool.parser.ecmascript.ASTVariableDeclarator;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;

public class FormalParameter extends CodeGenerator {

    public FormalParameter(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        ASTVarParameters varParameters=(ASTVarParameters)
                node.findFirstDownById(EcmaParserTreeConstants.JJTVARPARAMETERS,1);

        if(null!=varParameters)
        {
            this.emit(varParameters);
            this.emitSpace();
        }
        ASTVariableDeclarator variableDeclarator=
                (ASTVariableDeclarator)
                node.findFirstDownById(EcmaParserTreeConstants.JJTVARIABLEDECLARATOR,1);
        if(null!=variableDeclarator) {
            (new VariableDeclarator(this)).generate(variableDeclarator);
        }
    }
}