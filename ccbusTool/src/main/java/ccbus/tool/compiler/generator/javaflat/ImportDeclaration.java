package ccbus.tool.compiler.generator.javaflat;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.java.ASTName;
import ccbus.tool.parser.java.JavaParserConstants;
import ccbus.tool.parser.java.JavaParserTreeConstants;

public class ImportDeclaration extends CodeGenerator
{
    public ImportDeclaration(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node)
    {
        emitKind(JavaParserConstants.IMPORT);
        emitSpace();
        ASTName name=(ASTName)node.findFirstDownById(JavaParserTreeConstants.JJTNAME,1);
        LeafSequence leafSequence=new LeafSequence(this);
        leafSequence.setSeparator(kindToString(JavaParserConstants.DOT));
        leafSequence.generate(name);
        emitKind(JavaParserConstants.SEMICOLON);
    }
}
