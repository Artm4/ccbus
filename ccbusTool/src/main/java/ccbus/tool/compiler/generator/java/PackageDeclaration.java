package ccbus.tool.compiler.generator.java;

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
        emitKind(JavaParserConstants.PACKAGE);
        emitSpace();
        ASTName name=(ASTName)node.findFirstDownById(JavaParserTreeConstants.JJTNAME,1);
        LeafSequence leafSequence=new LeafSequence(this);
        leafSequence.setSeparator(kindToString(JavaParserConstants.DOT));
        leafSequence.generate(name);
        emitKind(JavaParserConstants.SEMICOLON);
        this.emitLn();
    }
}
