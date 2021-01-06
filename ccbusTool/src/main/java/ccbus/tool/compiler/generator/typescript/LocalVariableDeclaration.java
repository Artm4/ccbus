package ccbus.tool.compiler.generator.typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.ASTVariableDeclarator;
import ccbus.tool.parser.typescript.AngularParserConstants;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;

public class LocalVariableDeclaration extends CodeGenerator {

    public LocalVariableDeclaration(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node)
    {
        this.emitKind(AngularParserConstants.LET);
        this.emitSpace();

        ASTVariableDeclarator variableDeclarator=(ASTVariableDeclarator)
                node.findNextDownById(AngularParserTreeConstants.JJTVARIABLEDECLARATOR,1);
        while(null!=variableDeclarator)
        {
            (new VariableDeclarator(this)).generate(variableDeclarator);
            variableDeclarator=(ASTVariableDeclarator)
                    node.findNextDownById(AngularParserTreeConstants.JJTVARIABLEDECLARATOR,1);
        }
    }
}
