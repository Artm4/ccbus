package ccbus.tool.compiler.generator.typescript;

import ccbus.tool.parser.typescript.ASTCompilationUnit;
import ccbus.tool.parser.typescript.ASTTypeDeclaration;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;

public class CompilationUnit extends CodeGenerator
{
    public void generate(ASTCompilationUnit node)
    {
        ASTTypeDeclaration typeDeclaration=(ASTTypeDeclaration)node
                .findFirstDownById(AngularParserTreeConstants.JJTTYPEDECLARATION,4);
    }
}
