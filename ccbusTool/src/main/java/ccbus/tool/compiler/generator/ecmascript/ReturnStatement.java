package ccbus.tool.compiler.generator.ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;

public class ReturnStatement  extends CodeGenerator
{
    public ReturnStatement(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        for(int i=0;i<node.jjtGetNumChildren();i++) {
            this.recursiveGenerate(node.jjtGetChild(i));
            if(i==0)
            {
                this.emitSpace();
            }
        }
    }
}
