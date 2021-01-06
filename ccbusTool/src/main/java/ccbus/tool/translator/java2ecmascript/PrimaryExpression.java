package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.intermediate.SymTabEntry;
import ccbus.tool.intermediate.SymTabStack;
import ccbus.tool.parser.ecmascript.EcmaParserConstants;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.parser.java.*;
import ccbus.tool.translator.RecursiveHostTranslator;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.util.java.Tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrimaryExpression extends GenericTreeTranslator implements TreeTranslator,RecursiveHostTranslator
{
    SymTabStack symTabStack;
    Tool tool;

    public Node translate(Node node, TranslatedTree tree)
    {
        ASTPrimaryPrefix primaryPrefixResult=new ASTPrimaryPrefix(JavaParserTreeConstants.JJTPRIMARYPREFIX);
        tool=((Tool)tree.tool());
        symTabStack=tool.symTabStack();
        ASTPrimaryPrefix primaryPrefix=
                (ASTPrimaryPrefix)
                        node.findFirstDownById(JavaParserTreeConstants.JJTPRIMARYPREFIX,1);

        // changes only prefix
        translateName(tree,primaryPrefixResult,primaryPrefix);

        if(primaryPrefixResult.jjtGetNumChildren()>0)
        {
            primaryPrefix.resetChildren();
            for (int i = 0; i < primaryPrefixResult.jjtGetNumChildren(); i++)
            {
                primaryPrefix.add(primaryPrefixResult.jjtGetChild(i));
            }
        }



        ccbus.tool.parser.ecmascript.ASTPrimaryExpression primaryExpression=
                new ccbus.tool.parser.ecmascript.ASTPrimaryExpression(EcmaParserTreeConstants.JJTPRIMARYEXPRESSION);

        Node primaryPrefixResultEcma=
                tree.translateRecursive(primaryPrefix,this);
        primaryExpression.add(primaryPrefixResultEcma);

        ASTPrimarySuffix primarySuffix=
                (ASTPrimarySuffix)
                        node.findNextDownById(JavaParserTreeConstants.JJTPRIMARYSUFFIX,1);
        while(null!=primarySuffix)
        {

            Node primarySuffixResultEcma = tree.translateRecursive(primarySuffix, this);
            primaryExpression.add(primarySuffixResultEcma);
            primarySuffix=
                    (ASTPrimarySuffix)
                            node.findNextDownById(JavaParserTreeConstants.JJTPRIMARYSUFFIX,1);
        }

        // Amend INTEGER Literal after recursive translate
        Node literal=primaryPrefixResultEcma.findFirstDownById(EcmaParserTreeConstants.JJTLITERAL,1);

        if(null!=literal)
        {
            if(literal.jjtGetFirstToken().isKind(JavaParserConstants.INTEGER_LITERAL))
            {
                String imageOrig=literal.jjtGetFirstToken().image;

                Matcher m =  Pattern.compile("\\D").matcher(imageOrig);
                String imageEcma=m.replaceAll("");
                literal.jjtGetFirstToken().image=imageEcma;
            }
        }


        return primaryExpression;
    }

    private void translateName(TranslatedTree tree,ASTPrimaryPrefix primaryPrefixResult,
                               ASTPrimaryPrefix primaryPrefix
                              )
    {
        ASTName name=
                (ASTName)
                        primaryPrefix.findFirstDownById(JavaParserTreeConstants.JJTNAME,1);

        if(null==name)
        {
            return;
        }
        ASTIdentifier identifier=
                (ASTIdentifier)
                        name.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,1);

        translateAddThisPrefix(tree,primaryPrefixResult,identifier);

        for(int i=0;i<name.jjtGetNumChildren();i++)
        {
            primaryPrefixResult.add(name.jjtGetChild(i));
        }
    }

    private void translateAddThisPrefix(TranslatedTree tree,ASTPrimaryPrefix primaryPrefixResult,ASTIdentifier identifier)
    {
        SymTabEntry symTabEntry=symTabStack.lookup(identifier.image());
        if(null==symTabEntry)
        {
            if(null!=symTabStack.lookupSymTab(identifier.image()))
            {
                // it is class/interface no need of this prefix
                return;
            }
            // should throw error
            tool.errorTranslate(identifier,"Name \""+identifier.image()+"\" not declared");
        }

        // it is in method declaration TO CHECK
        if(!symTabEntry.getSymTab().isClass())
        {
            return;
        }

        ASTThisToken thisToken=new ASTThisToken(JavaParserTreeConstants.JJTTHISTOKEN);
        Token tokenIdentifier= Token.newToken(
                JavaParserConstants.THIS, "this");
        thisToken.jjtSetFirstToken(tokenIdentifier);
        thisToken.jjtSetLastToken(tokenIdentifier);

        ASTDotToken dotNode=((Tool)tree.tool()).createNodeDot();

        primaryPrefixResult.add(thisToken);
        primaryPrefixResult.add(dotNode);

    }
}
