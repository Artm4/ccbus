package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;
import ccbus.tool.parser.typescript.ASTCompilationUnit;
import ccbus.tool.parser.java.JavaParserTreeConstants;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.TranslatedTree;

public class CompilationUnit implements TreeTranslator
{
    public Node translate(Node node, TranslatedTree tree)
    {
        ASTCompilationUnit compilationUnitResult=new ASTCompilationUnit(AngularParserTreeConstants.JJTCOMPILATIONUNIT);
        tree.add(compilationUnitResult);

        Node typeDeclaration=tree.translate(JavaParserTreeConstants.JJTTYPEDECLARATION,node);
        compilationUnitResult.add(typeDeclaration);
        return compilationUnitResult;
    }
}
