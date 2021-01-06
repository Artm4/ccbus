package ccbus.tool.compiler.generator.ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.ASTImportDeclaration;
import ccbus.tool.parser.ecmascript.ASTTypeDeclaration;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;

public class CompilationUnit extends CodeGenerator
{
    public CompilationUnit(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    public void generate(Node node)
    {
        boolean preImport=this.preClassDeclaration.size()>0;
        this.firePreClassDeclaration();
        if(preImport)
        {
            this.emitLn();
            this.emitLn();
        }
        ASTImportDeclaration importDeclaration=(ASTImportDeclaration) node
                .findNextDownById(EcmaParserTreeConstants.JJTIMPORTDECLARATION,1);
        boolean imported=null!=importDeclaration;

        while(null!=importDeclaration)
        {
            (new ImportDeclaration(this)).generate(importDeclaration);
            this.emitLn();

            importDeclaration=(ASTImportDeclaration) node
                    .findNextDownById(EcmaParserTreeConstants.JJTIMPORTDECLARATION,1);
        }
        if(imported)
        {
            this.emitLn();
        }

        ASTTypeDeclaration typeDeclaration=(ASTTypeDeclaration)node
                .findFirstDownById(EcmaParserTreeConstants.JJTTYPEDECLARATION,1);
        (new ClassDeclaration(this)).generate(typeDeclaration);
    }
}
