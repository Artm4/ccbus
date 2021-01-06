package ccbus.tool.compiler.generator.typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.*;

public class FormalParameter extends CodeGenerator {

    public FormalParameter(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        ASTVarParameters varParameters=(ASTVarParameters)
                node.findFirstDownById(AngularParserTreeConstants.JJTVARPARAMETERS,1);

        if(null!=varParameters)
        {
            this.emit(varParameters);
            this.emitSpace();
        }
        ASTVariableDeclarator variableDeclarator=
                (ASTVariableDeclarator)
                node.findFirstDownById(AngularParserTreeConstants.JJTVARIABLEDECLARATOR,1);
        if(null!=variableDeclarator) {
            (new VariableDeclarator(this)).generate(variableDeclarator);
        }
    }
}