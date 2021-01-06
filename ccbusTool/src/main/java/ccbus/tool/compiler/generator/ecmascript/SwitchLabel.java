package ccbus.tool.compiler.generator.ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;

public class SwitchLabel extends CodeGenerator {

    public SwitchLabel(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        for(int i=0;i<node.jjtGetNumChildren();i++)
        {
            recursiveGenerate(node.jjtGetChild(i));
            if(node.jjtGetChild(i).getId()==EcmaParserTreeConstants.JJTCASETOKEN)
            {
                this.emitSpace();
            }
        }
    }
}
