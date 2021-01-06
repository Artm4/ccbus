package ccbus.tool.compiler.generator.ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.ASTVariableDeclarator;
import ccbus.tool.parser.ecmascript.EcmaParserConstants;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;

public class LocalVariableDeclaration extends CodeGenerator {

    public LocalVariableDeclaration(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node)
    {
        (new TypeDoc(this)).generate(node.findFirstDownById(EcmaParserTreeConstants.JJTTYPE,2));

        this.emitKind(EcmaParserConstants.LET);
        this.emitSpace();

        ASTVariableDeclarator variableDeclarator=(ASTVariableDeclarator)
                node.findNextDownById(EcmaParserTreeConstants.JJTVARIABLEDECLARATOR,1);
        while(null!=variableDeclarator)
        {
            (new VariableDeclarator(this)).generate(variableDeclarator);
            variableDeclarator=(ASTVariableDeclarator)
                    node.findNextDownById(EcmaParserTreeConstants.JJTVARIABLEDECLARATOR,1);
        }
    }
}
