package ccbus.tool.compiler.generator.typescript;

import ccbus.tool.intermediate.Node;

public class Statement extends CodeGenerator {

    public Statement(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        this.recursiveGenerate(node);
    }
}
