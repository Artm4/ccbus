package ccbus.tool.compiler.generator.ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.EcmaParserConstants;

public class ArrayInitializer extends CodeGenerator {

    public ArrayInitializer(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        this.emitKind(EcmaParserConstants.LBRACKET);
        for(int i=0;i<node.jjtGetNumChildren();i++) {
            this.recursiveGenerate(node.jjtGetChild(i));
        }
        this.emitKind(EcmaParserConstants.RBRACKET);
    }
}
