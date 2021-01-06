package ccbus.tool.compiler.generator.typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.AngularParserConstants;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;
import ccbus.tool.intermediate.SimpleNode;

public class VariableDeclarator  extends CodeGenerator{

    public VariableDeclarator(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node)
    {
        VariableDeclaratorId variableDeclaratorIdGen=new VariableDeclaratorId(this);
        variableDeclaratorIdGen.generate(
                node.findFirstDownById(AngularParserTreeConstants.JJTVARIABLEDECLARATORID,1)
        );

        this.emitSpace();
        this.emitKind(AngularParserConstants.COLON);
        this.emitSpace();

        Type typeGen=new Type(this);
        typeGen.generate(
                node.findFirstDownById(AngularParserTreeConstants.JJTTYPE,1)
        );

        SimpleNode variableInitializer=(SimpleNode)
                node.findFirstDownById(AngularParserTreeConstants.JJTVARIABLEINITIALIZER,1);
        if(null!=variableInitializer) {
            this.emitSpace();
            this.emitKind(AngularParserConstants.ASSIGN);
            this.emitSpace();
            this.recursiveGenerate(variableInitializer);
        }
    }
}
