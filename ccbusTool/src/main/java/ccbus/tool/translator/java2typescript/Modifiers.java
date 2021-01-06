package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;

import ccbus.tool.parser.typescript.*;
import ccbus.tool.parser.java.JavaParserConstants;
import ccbus.tool.parser.java.JavaParserTreeConstants;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.TranslatedTree;

public class Modifiers implements TreeTranslator
{
    public Node translate(Node node,TranslatedTree tree)
    {
        ASTModifiers nodeResult=new ASTModifiers(AngularParserTreeConstants.JJTMODIFIERS);
        tree.add(nodeResult);

        for(int i=0;i<5;i++)
        {
            Node modifierNode=node.findNextDownById(JavaParserTreeConstants.JJTMODIFIER,2);
            if(modifierNode==null){break;}

            if(node.jjtGetParent().getId()==JavaParserTreeConstants.JJTTYPEDECLARATION)
            {
                if(modifierNode.jjtGetFirstToken().isKind(JavaParserConstants.PUBLIC))
                {
                    ASTModifier modifierNodeResult=new ASTModifier(AngularParserTreeConstants.JJTMODIFIER);
                    modifierNodeResult.jjtSetFirstToken(Token.newTokenInit(AngularParserConstants.EXPORT));
                    nodeResult.add(modifierNodeResult);
                }
            }
            else {
                int modifier=0;
                if (modifierNode.jjtGetFirstToken().isKind(JavaParserConstants.PUBLIC))
                {
                    modifier=AngularParserConstants.PUBLIC;
                }
                if (modifierNode.jjtGetFirstToken().isKind(JavaParserConstants.STATIC))
                {
                    modifier=AngularParserConstants.STATIC;
                }
                if (modifierNode.jjtGetFirstToken().isKind(JavaParserConstants.PROTECTED))
                {
                    modifier=AngularParserConstants.PROTECTED;
                }
                if (modifierNode.jjtGetFirstToken().isKind(JavaParserConstants.PRIVATE))
                {
                    modifier=AngularParserConstants.PRIVATE;
                }
                if(0==modifier)
                {
                    continue;
                }
                ASTModifier modifierNodeResult = new ASTModifier(AngularParserTreeConstants.JJTMODIFIER);
                modifierNodeResult.jjtSetFirstToken(Token.newTokenInit(modifier));
                nodeResult.add(modifierNodeResult);
            }
        }
        return nodeResult;
    }
}