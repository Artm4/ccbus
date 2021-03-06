package ccbus.tool.compiler.generator.ecmascript;

import ccbus.tool.intermediate.Node;

public class ForStatement extends CodeGenerator {

    public ForStatement(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        for(int i=0;i<node.jjtGetNumChildren();i++) {
            this.recursiveGenerate(node.jjtGetChild(i));
        }
    }
}
