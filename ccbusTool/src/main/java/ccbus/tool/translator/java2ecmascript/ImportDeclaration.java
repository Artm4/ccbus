package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.*;
import ccbus.tool.parser.ecmascript.ASTIdentifier;
import ccbus.tool.parser.ecmascript.ASTImportDeclaration;
import ccbus.tool.parser.ecmascript.Token;
import ccbus.tool.parser.java.*;
import ccbus.tool.parser.java.ASTAnnotation;
import ccbus.tool.parser.java.ASTCompilationUnit;
import ccbus.tool.parser.java.ASTName;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.util.java.Misc;
import ccbus.tool.util.Pair;

import java.io.File;

public class ImportDeclaration implements TreeTranslator {
    public Node translate(Node node, TranslatedTree tree)
    {
        ASTImportDeclaration nodeResult=new ASTImportDeclaration(EcmaParserTreeConstants.JJTIMPORTDECLARATION);
        nodeResult.jjtSetFirstToken(node.jjtGetFirstToken());
        tree.add(nodeResult);

        ASTName name=(ASTName)node.findFirstDownById(JavaParserTreeConstants.JJTNAME);
        ASTStar star=(ASTStar) node.findFirstDownById(JavaParserTreeConstants.JJTSTAR);
        ASTNameEff nameEff=(ASTNameEff)node.findFirstDownById(JavaParserTreeConstants.JJTNAMEEFF);

        Node namePackage=name;
        if(null!=nameEff)
        {
            namePackage=nameEff;
        }

        String className;
        String packageName;

        if(null!=star)
        {
            translateWildImport(nodeResult,name,nameEff,(ccbus.tool.util.java.Tool) tree.tool());
        }
        else {
            Pair<String,String> pathAndFile=Misc.extractPackageName(namePackage,(ccbus.tool.util.java.Tool)tree.tool());
            className=pathAndFile.getValue().replaceFirst(".java","");
            packageName=pathAndFile.getKey();
            ASTCompilationUnit compilationUnit = (ASTCompilationUnit) ((ccbus.tool.util.java.Tool)
                    tree.tool()).parsePackageName(name);
            translateCompilationUnit(nodeResult, compilationUnit, packageName,className);
        }
        return nodeResult;
    }

    private void translateWildImport(ASTImportDeclaration nodeResult, ASTName name, ASTNameEff nameEff, ccbus.tool.util.java.Tool tool)
    {
        Pair<String,String> pathAndFile=Misc.extractPackageNameWild(name,tool);
        Pair<String,String> pathAndFileEff=pathAndFile;
        if(null!=nameEff)
        {
            pathAndFileEff=Misc.extractPackageNameWild(nameEff,tool);
        }
        try{
            String srcPath=Misc.absolutePath(pathAndFile.getKey(),pathAndFile.getValue(),tool);
            File path = new File(
                srcPath
            );
            String[] list = path.list();
            for(int i = 0; i < list.length; i++)
            {
                if(list[i].endsWith(".java"))
                {
                    ASTCompilationUnit compilationUnit = (ASTCompilationUnit) tool.parseFile(srcPath + tool.getFileSeparator() + list[i]);
                    String className = list[i].replaceFirst(".java", "");
                    translateCompilationUnit(nodeResult, compilationUnit, pathAndFileEff.getKey(), className);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isAnnotationEqual(ASTAnnotation astAnnotation,String image)
    {
        return null!=astAnnotation && astAnnotation.findLeaf().image().equals(image);
    }

    private void translateCompilationUnit(ASTImportDeclaration nodeResult,
                                          ASTCompilationUnit compilationUnit,
                                          String pathName,
                                          String className)
    {
        ASTAnnotation astAnnotation=
                (ASTAnnotation)
                        compilationUnit.findNextDownById(JavaParserTreeConstants.JJTANNOTATION,5);

        ASTAnnotation astAnnotationSecond=
                (ASTAnnotation)
                        compilationUnit.findNextDownById(JavaParserTreeConstants.JJTANNOTATION,5);

        compilationUnit.resetNextSearch();
        boolean defaultImport=false;
        boolean moduleImport=false;

        if(isAnnotationEqual(astAnnotation,"ExportDefault")
                || isAnnotationEqual(astAnnotationSecond,"ExportDefault"))
        {
            defaultImport=true;
        }

        if(isAnnotationEqual(astAnnotation,"ExportModule")
                || isAnnotationEqual(astAnnotationSecond,"ExportModule"))
        {
            moduleImport=true;
        }

        Token identifierToken=Token.newToken(EcmaParserConstants.IDENTIFIER,className);

        ASTIdentifier identifier=new ASTIdentifier(EcmaParserTreeConstants.JJTIDENTIFIER);
        identifier.jjtSetFirstToken(identifierToken);
        identifier.jjtSetLastToken(identifierToken);

        if(defaultImport)
        {
            ASTImportDefault importDefault=new ASTImportDefault(EcmaParserTreeConstants.JJTIMPORTDEFAULT);
            importDefault.add(identifier);
            nodeResult.add(importDefault);
        }
        else
        {
            ASTImportNamed importNamed=new ASTImportNamed(EcmaParserTreeConstants.JJTIMPORTNAMED);
            importNamed.add(identifier);
            nodeResult.add(importNamed);
        }

        pathName=pathName.replaceAll("_","-");
        pathName=pathName.replaceAll("\\\\",Misc.genericSeparator);
        if(false==moduleImport)
        {
            pathName=pathName+Misc.genericSeparator+className;
        }
        Token stringLiteralToken=Token.newToken(EcmaParserConstants.STRING_LITERAL,pathName);
        ASTStringLiteral stringLiteral=new ASTStringLiteral(EcmaParserTreeConstants.JJTSTRINGLITERAL);
        stringLiteral.jjtSetFirstToken(stringLiteralToken);
        stringLiteral.jjtSetLastToken(stringLiteralToken);

        nodeResult.add(stringLiteral);
    }

}
