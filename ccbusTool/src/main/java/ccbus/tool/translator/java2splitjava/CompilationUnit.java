package ccbus.tool.translator.java2splitjava;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.java.ASTCompilationUnit;
import ccbus.tool.parser.java.ASTPackageDeclaration;
import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.RecursiveHostTranslator;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.java2reactserverjava.CompilationNodeValue;
import ccbus.tool.translator.java2reactserverjava.CompilationUnitNodePair;
import ccbus.tool.translator.java2splitjava.GenericTreeTranslator;
import ccbus.tool.util.java.Tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompilationUnit extends GenericTreeTranslator implements TreeTranslator ,RecursiveHostTranslator
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

        setPackageClient(compilationUnit,tool,  relativePath);
    }

    private void setPackageClient(ASTCompilationUnit compilationUnitClient,Tool tool,ArrayList<String> relativePath)
    {
        String rootPackagePlain=tool.getConfigParser().getClientRootPackage();
        String[] rootPackageSplit=rootPackagePlain.split("\\.");

        ArrayList<String> fullPath=new ArrayList<>(Arrays.asList(rootPackageSplit));
        fullPath.addAll(relativePath);

        CompilationNodeValue compilationNodeValue=new CompilationNodeValue();
        compilationNodeValue.setRelativePath(fullPath);
        compilationUnitClient.jjtSetValue(compilationNodeValue);

        String [] packageClient=fullPath.toArray(new String[]{});
         //   String [] packageClient={"dev","uib","service"};
            ASTPackageDeclaration packageDeclaration =
                    tool.createPackageDeclaration(tool.createName(packageClient),null);


            ASTPackageDeclaration prevPackage=
                    (ASTPackageDeclaration)compilationUnitClient
                            .findFirstDownById(JavaParserTreeConstants.JJTPACKAGEDECLARATION);
            prevPackage.detach();

            compilationUnitClient.add(packageDeclaration);
    }
}
