package ccbus.tool.compiler.generator.typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.AngularParserConstants;

public class ArrayInitializer extends CodeGenerator {

    public ArrayInitializer(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        this.emitKind(AngularParserConstants.LBRACKET);
        for(int i=0;i<node.jjtGetNumChildren();i++) {
            this.recursiveGenerate(node.jjtGetChild(i));
        }
        this.emitKind(AngularParserConstants.RBRACKET);
    }
}
