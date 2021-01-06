package ccbus.tool.compiler.generator.ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.intermediate.SimpleNode;

import ccbus.tool.parser.ecmascript.EcmaParserConstants;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;

public class VariableDeclarator  extends CodeGenerator {

    public VariableDeclarator(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node)
    {
        VariableDeclaratorId variableDeclaratorIdGen=new VariableDeclaratorId(this);
        variableDeclaratorIdGen.generate(
                node.findFirstDownById(EcmaParserTreeConstants.JJTVARIABLEDECLARATORID,1)
        );

        SimpleNode variableInitializer=(SimpleNode)
                node.findFirstDownById(EcmaParserTreeConstants.JJTVARIABLEINITIALIZER,1);
        if(null!=variableInitializer) {
            this.emitSpace();
            this.emitKind(EcmaParserConstants.ASSIGN);
            this.emitSpace();
            this.recursiveGenerate(variableInitializer);
        }
    }
}
