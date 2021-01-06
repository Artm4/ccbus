package ccbus.tool.compiler.generator.typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.AngularParserConstants;

public class TypeParameters extends LeafSequence
{
    public TypeParameters(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node)
    {
        this.setSeparator(kindToString(AngularParserConstants.COMMA));
        emitKind(AngularParserConstants.LT);
        super.generate(node);
        emitKind(AngularParserConstants.GT);
    }
}
