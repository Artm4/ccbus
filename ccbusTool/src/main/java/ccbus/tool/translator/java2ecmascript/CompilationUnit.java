package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.ASTCompilationUnit;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.parser.java.ASTImportDeclaration;
import ccbus.tool.parser.java.ASTTypeDeclaration;
import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;

public class CompilationUnit implements TreeTranslator
{
    public Node translate(Node node, TranslatedTree tree)
    {
        ASTCompilationUnit compilationUnitResult=new ASTCompilationUnit(EcmaParserTreeConstants.JJTCOMPILATIONUNIT);
        tree.add(compilationUnitResult);

        ASTTypeDeclaration typeDeclaration=
                (ASTTypeDeclaration)
                node.findFirstDownById(JavaParserTreeConstants.JJTTYPEDECLARATION,1);
        Node typeDeclarationResult=tree.translate(JavaParserTreeConstants.JJTTYPEDECLARATION,typeDeclaration);
        compilationUnitResult.add(typeDeclarationResult);

        ASTImportDeclaration importDeclaration=(ASTImportDeclaration)node.findNextDownById(JavaParserTreeConstants.JJTIMPORTDECLARATION,1);
        while(null!=importDeclaration)
        {
            ccbus.tool.parser.ecmascript.ASTImportDeclaration importDeclarationResult=
                    (ccbus.tool.parser.ecmascript.ASTImportDeclaration) tree.translate(JavaParserTreeConstants.JJTIMPORTDECLARATION,importDeclaration);
            compilationUnitResult.add(importDeclarationResult);

            importDeclaration=(ASTImportDeclaration)node.findNextDownById(JavaParserTreeConstants.JJTIMPORTDECLARATION,1);
        }


        return compilationUnitResult;
    }
}
