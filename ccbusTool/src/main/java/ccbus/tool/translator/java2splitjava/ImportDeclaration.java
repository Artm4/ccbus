package ccbus.tool.translator.java2splitjava;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.java.*;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.util.Pair;
import ccbus.tool.util.java.Misc;
import ccbus.tool.util.java.PostAction;
import ccbus.tool.util.java.Tool;

import java.io.File;

public class ImportDeclaration extends ccbus.tool.translator.java2reactserverjava.ImportDeclaration
{

    private Tool tool;

    public Node translate(Node node, TranslatedTree tree)
    {
        ASTName name=(ASTName)node.findFirstDownById(JavaParserTreeConstants.JJTNAME);
        ASTStar star=(ASTStar) node.findFirstDownById(JavaParserTreeConstants.JJTSTAR);
        ASTNameEff astNameEff=null;

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
            astNameEff=tool.removePrefix(name,tool.getCcbusPackageBase());
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
        else
        if(tool.isDesktopPackage(name))
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

        if(null!=astNameEff)
        {
            node.jjtSetFirstToken(null);
            name.resetChildren();
            name.jjtSetFirstToken(null);
            name.jjtSetLastToken(null);
            for(int i=0;i<tool.getRootPackageSplit().length;i++)
            {
                name.add(tool.createIdentifier(tool.getClientPackage()[i]));
            }
           for(int i=0;i<astNameEff.jjtGetNumChildren();i++)
           {
               name.add(astNameEff.jjtGetChild(i));
           }
        }

        return null;
    }

}
