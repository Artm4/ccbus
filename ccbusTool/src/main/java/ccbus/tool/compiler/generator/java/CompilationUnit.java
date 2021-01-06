package ccbus.tool.compiler.generator.java;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.java.*;

public class CompilationUnit extends CodeGenerator
{
    public CompilationUnit(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    public void generate(Node node)
    {
        ASTPackageDeclaration packageDeclaration=(ASTPackageDeclaration)node
                .findFirstDownById(JavaParserTreeConstants.JJTPACKAGEDECLARATION,1);

        ASTTypeDeclaration typeDeclaration=(ASTTypeDeclaration)node
                .findFirstDownById(JavaParserTreeConstants.JJTTYPEDECLARATION,1);

        ASTImportDeclaration importDeclaration=(ASTImportDeclaration)node
                .findNextDownById(JavaParserTreeConstants.JJTIMPORTDECLARATION,1);

        if(null!=packageDeclaration)
        {
            (new PackageDeclaration(this)).generate(packageDeclaration);
        }

        boolean importGenerated=false;
        while(null!=importDeclaration)
        {
            importGenerated=true;
            if(importDeclaration.jjtGetFirstToken()==null)
            {
                this.emitLn();
                (new ImportDeclaration(this)).generate(importDeclaration);
            }
            else
            {
                emitTokenList(importDeclaration);
            }
            importDeclaration=(ASTImportDeclaration)node
                    .findNextDownById(JavaParserTreeConstants.JJTIMPORTDECLARATION,1);
        }

        if(importGenerated)
        {
            emitLn();
            emitLn();
        }


        (new ClassDeclaration(this)).generate(typeDeclaration);


    }
}
