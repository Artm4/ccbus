package ccbus.tool.translator.java2reactserverjava;

import ccbus.tool.intermediate.Node;

import ccbus.tool.parser.java.ASTCompilationUnit;
import ccbus.tool.parser.java.ASTName;
import ccbus.tool.parser.java.*;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.util.java.Misc;
import ccbus.tool.util.java.Tool;
import ccbus.tool.util.java.PostAction;
import ccbus.tool.util.Pair;


import java.io.File;

public class ImportDeclaration implements TreeTranslator {

    private Tool tool;

    public Node translate(Node node, TranslatedTree tree)
    {
        ASTName name=(ASTName)node.findFirstDownById(JavaParserTreeConstants.JJTNAME);
        ASTStar star=(ASTStar) node.findFirstDownById(JavaParserTreeConstants.JJTSTAR);

        tool=((Tool)tree.tool());

        // It is not client but server package add it to server code and detach it from client
        if(!tool.isClientPackage(name))
        {
            PostAction<Node,Tool,Node> postAction =new PostAction<>(node,tool,(Node n, Tool t)->
            {
                n.detach();
                t.getCompilationUnitNodePair().addToAllServer(n);
                return null;

            });
            tool.postAction.add(postAction);
            return null;
        }
        else // It is client/server package case add to server/client
        if(tool.isSharePackage(name))
        {
            this.translateShared(node,tool);
        }
        else // it is system package that is related only to server side or implicite ecma package , detach from client
        if(tool.isSystemPackage(name) || tool.isEcmaPackage(name))
        {
            PostAction<Node,Tool,Node> postAction =new PostAction<>(node,tool,(Node n, Tool t)->
            {
                n.detach();
                return null;

            });
            tool.postAction.add(postAction);
        }
        else // It is ccbus core package that should be server and client with same API
        if(tool.isCoreSharedPackage(name))
        {
            PostAction<Node,Tool,Node> postAction =new PostAction<>(node,tool,(Node n, Tool t)->
            {
                t.getCompilationUnitNodePair().addToAllServer(n);
                return null;

            });
            tool.postAction.add(postAction);
        }

        ASTNameEff astNameEff=tool.removePrefix(name);


        if(null!=astNameEff)
        {
            node.add(astNameEff);
            // translate package naming for compiled units, from java package to client package
            // Check if it is project package not ccbus connect, because both effective names are the same
            if(!tool.isCoreSharedPackage(name)) {
                tool.translateClientPackageSuffix(astNameEff);
            }
        }

        if(null!=star)
        {
            translateWildImport(name,tree);
        }
        else {
            ASTCompilationUnit compilationUnit = (ASTCompilationUnit) ((Tool)
                    tree.tool()).parsePackageName(name);

            translateRecursiveImport(compilationUnit,tree);
        }
        return null;
    }


    public Node translatePlain(Node node, TranslatedTree tree)
    {
        ASTName name=(ASTName)node.findFirstDownById(JavaParserTreeConstants.JJTNAME);
        ASTStar star=(ASTStar) node.findFirstDownById(JavaParserTreeConstants.JJTSTAR);

        tool=((Tool)tree.tool());

        // It is not client but server package add it to server code and detach it from client
        if(!tool.isClientPackage(name))
        {
            return null;
        }

        if(null!=star)
        {
            translateWildImport(name,tree);
        }
        else {
            ASTCompilationUnit compilationUnit = (ASTCompilationUnit) ((Tool)
                    tree.tool()).parsePackageName(name);

            translateRecursiveImport(compilationUnit,tree);
        }
        return null;
    }

    protected void translateRecursiveImport(ASTCompilationUnit compilationUnit,TranslatedTree tree)
    {
        ASTImportDeclaration importDeclaration=(ASTImportDeclaration)
                compilationUnit.findNextDownById(JavaParserTreeConstants.JJTIMPORTDECLARATION,1);

        while(null!=importDeclaration)
        {
            translatePlain(importDeclaration, tree);
            importDeclaration=(ASTImportDeclaration)
                    compilationUnit.findNextDownById(JavaParserTreeConstants.JJTIMPORTDECLARATION,1);
        }
    }

    protected ASTImportDeclaration translateShared(Node node,Tool tool)
    {
        ASTImportDeclaration importDeclaration=(ASTImportDeclaration)
           // node.createCopyGeneric(()->new ASTImportDeclaration(JavaParserTreeConstants.JJTIMPORTDECLARATION));
                new ASTImportDeclaration(JavaParserTreeConstants.JJTIMPORTDECLARATION);
        // set first token to null to make it by node generation ,not tokens
        importDeclaration.jjtSetFirstToken(null);
        ASTName nameOrig=(ASTName) node.findFirstDownById(JavaParserTreeConstants.JJTNAME);
        // keep worker, create new without prefix for client
        ASTName name=(ASTName) nameOrig.createCopyGeneric(()->new ASTName(JavaParserTreeConstants.JJTNAME));
        for(int i=0;i<nameOrig.jjtGetNumChildren();i++)
        {
            //name.add(tool.cloneNode(nameOrig.jjtGetChild(i)));
            name.add(nameOrig.jjtGetChild(i));
        }
        importDeclaration.add(name);

        PostAction<Node,Tool,Node> postAction =new PostAction<>(importDeclaration,tool,(Node n, Tool t)->
        {
            t.getCompilationUnitNodePair().addToAllServer(n);
            return null;

        });
        tool.postAction.add(postAction);
        return importDeclaration;
    }

    protected void translateWildImport(ASTName name,TranslatedTree tree)
    {
        tool=((Tool)tree.tool());
        Pair<String,String> pathAndFile=Misc.extractPackageNameWild(name,tool);
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
                    translateRecursiveImport(compilationUnit,tree);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
