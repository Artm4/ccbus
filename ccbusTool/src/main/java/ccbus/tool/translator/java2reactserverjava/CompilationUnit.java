package ccbus.tool.translator.java2reactserverjava;

import ccbus.tool.intermediate.Node;

import ccbus.tool.parser.java.*;
import ccbus.tool.translator.RecursiveHostTranslator;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.util.java.Tool;

import java.util.ArrayList;

public class CompilationUnit  extends GenericTreeTranslator implements TreeTranslator ,RecursiveHostTranslator
{
    public Node translate(Node node, TranslatedTree tree)
    {
        return null;
    }

    public CompilationUnitNodePair translateClientServer(Node node, TranslatedTree tree)
    {
        Tool tool=((Tool) tree.tool());
        CompilationUnitNodePair compilationUnitNodePair =new CompilationUnitNodePair();
        ((Tool)tree.tool()).setCompilationUnitNodePair(compilationUnitNodePair);

        ASTCompilationUnit compilationUnitClient=(ASTCompilationUnit)tree.translateRecursive(node,this);

        compilationUnitNodePair.setClientCompilationUnit(compilationUnitClient);
        setCompilationUnitRelativePath(compilationUnitClient,tool);

        tool.firePostActions();
        tool.setCompilationUnitNodePair(null);

        return compilationUnitNodePair;
    }

    private void setCompilationUnitRelativePath(ASTCompilationUnit compilationUnit,Tool tool)
    {
        ArrayList relativePath=tool.parseCompilationUnitRelativePathClient(compilationUnit);

        CompilationNodeValue compilationNodeValue=new CompilationNodeValue();
        compilationNodeValue.setRelativePath(relativePath);
        compilationUnit.jjtSetValue(compilationNodeValue);
    }
}
