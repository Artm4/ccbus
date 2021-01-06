package ccbus.tool.compiler.generator.typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.AngularParserConstants;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;

public class FieldDeclaration extends CodeGenerator{

    public FieldDeclaration(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node)
    {
        VariableDeclarator variableDeclaratorGen=new VariableDeclarator(this);
        variableDeclaratorGen.generate(
                node.findFirstDownById(AngularParserTreeConstants.JJTVARIABLEDECLARATOR,1));
        this.emitKind(AngularParserConstants.SEMICOLON);
    }
}
