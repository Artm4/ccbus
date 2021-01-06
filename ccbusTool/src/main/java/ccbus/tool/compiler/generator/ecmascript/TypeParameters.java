package ccbus.tool.compiler.generator.ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.EcmaParserConstants;

public class TypeParameters extends LeafSequence
{
    public TypeParameters(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node)
    {
        this.setSeparator(kindToString(EcmaParserConstants.COMMA));
        emitKind(EcmaParserConstants.LT);
        super.generate(node);
        emitKind(EcmaParserConstants.GT);
    }
}
