package ccbus.tool.compiler.generator.typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;

public class SwitchLabel extends CodeGenerator {

    public SwitchLabel(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        for(int i=0;i<node.jjtGetNumChildren();i++)
        {
            recursiveGenerate(node.jjtGetChild(i));
            if(node.jjtGetChild(i).getId()==AngularParserTreeConstants.JJTCASETOKEN)
            {
                this.emitSpace();
            }
        }
    }
}
