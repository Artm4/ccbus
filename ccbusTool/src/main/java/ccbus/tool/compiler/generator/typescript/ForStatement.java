package ccbus.tool.compiler.generator.typescript;

import ccbus.tool.intermediate.Node;

public class ForStatement extends CodeGenerator {

    public ForStatement(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        for(int i=0;i<node.jjtGetNumChildren();i++) {
            this.recursiveGenerate(node.jjtGetChild(i));
//            if(node.jjtGetChild(i).getId()==AngularParserTreeConstants.JJTRPARENTOKEN)
//            {
//                this.emitLn();
//            }
        }
    }
}
