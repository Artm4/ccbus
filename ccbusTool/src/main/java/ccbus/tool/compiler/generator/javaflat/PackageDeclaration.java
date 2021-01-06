package ccbus.tool.compiler.generator.javaflat;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.java.ASTName;
import ccbus.tool.parser.java.JavaParserConstants;
import ccbus.tool.parser.java.JavaParserTreeConstants;

public class PackageDeclaration extends CodeGenerator
{
    public PackageDeclaration(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node)
    {
        emitTokenList(node);
    }
}
