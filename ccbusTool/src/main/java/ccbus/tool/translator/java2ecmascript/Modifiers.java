package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.*;
import ccbus.tool.parser.java.JavaParserConstants;
import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;

public class Modifiers implements TreeTranslator
{
    public Node translate(Node node,TranslatedTree tree)
    {
        ASTModifiers nodeResult=new ASTModifiers(EcmaParserTreeConstants.JJTMODIFIERS);
        tree.add(nodeResult);

        for(int i=0;i<5;i++)
        {
            Node modifierNode=node.findNextDownById(JavaParserTreeConstants.JJTMODIFIER,2);
            if(modifierNode==null){break;}

            if(node.jjtGetParent().getId()==JavaParserTreeConstants.JJTTYPEDECLARATION)
            {
                if(modifierNode.jjtGetFirstToken().isKind(JavaParserConstants.PUBLIC))
                {
                    ASTModifier modifierNodeResult=new ASTModifier(EcmaParserTreeConstants.JJTMODIFIER);
                    modifierNodeResult.jjtSetFirstToken(Token.newTokenInit(EcmaParserConstants.EXPORT));
                    nodeResult.add(modifierNodeResult);
                }
                else
                if(modifierNode.getId()==JavaParserTreeConstants.JJTANNOTATION)
                {
                    ccbus.tool.parser.java.ASTName annotationName=
                            (ccbus.tool.parser.java.ASTName)
                            modifierNode.findNextDownById(JavaParserTreeConstants.JJTNAME,1);
                    if(null!=annotationName && annotationName.findLeaf().image().equals("ExportDefault"))
                    {
                        ASTModifier modifierNodeResult=new ASTModifier(EcmaParserTreeConstants.JJTMODIFIER);
                        modifierNodeResult.jjtSetFirstToken(Token.newTokenInit(EcmaParserConstants.DEFAULT));
                        nodeResult.add(modifierNodeResult);
                    }
                }
            }
            else {
                int modifier=0;
                if (modifierNode.jjtGetFirstToken().isKind(JavaParserConstants.STATIC))
                {
                    modifier=EcmaParserConstants.STATIC;
                }
                if(0==modifier)
                {
                    continue;
                }
                ASTModifier modifierNodeResult = new ASTModifier(EcmaParserTreeConstants.JJTMODIFIER);
                modifierNodeResult.jjtSetFirstToken(Token.newTokenInit(modifier));
                nodeResult.add(modifierNodeResult);
            }
        }
        return nodeResult;
    }
}