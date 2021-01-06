package ccbus.tool.compiler.generator.typescript;

import ccbus.tool.intermediate.Node;

public class StatementExpression extends CodeGenerator {

    public StatementExpression(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        for(int i=0;i<node.jjtGetNumChildren();i++) {
            this.recursiveGenerate(node.jjtGetChild(i));
        }
    }
}
