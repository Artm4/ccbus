package ccbus.tool.compiler.generator.java;

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
        for(int i=0;i<name.jjtGetNumChildren();i++)
        {
            if(name.jjtGetChild(i).getId()==JavaParserTreeConstants.JJTIDENTIFIER)
            {
                this.emit(name.jjtGetChild(i));
                if(i<name.jjtGetNumChildren()-1)
                {
                    this.emitKind(JavaParserConstants.DOT);
                }
            }

        }
        emitKind(JavaParserConstants.SEMICOLON);
    }
}
