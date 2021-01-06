package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.intermediate.SimpleNode;
import ccbus.tool.parser.typescript.ASTType;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;
import ccbus.tool.parser.java.ASTBracket;
import ccbus.tool.parser.java.ASTVariableDeclarator;
import ccbus.tool.parser.java.ASTVariableDeclaratorId;
import ccbus.tool.parser.java.JavaParserTreeConstants;

import ccbus.tool.translator.RecursiveHostTranslator;
import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.TranslatedTree;

public class VariableDeclarator  extends GenericTreeTranslator implements TreeTranslator ,RecursiveHostTranslator
{
    public Node translate(Node node, TranslatedTree tree)
    {
        return translate((ASTVariableDeclarator) node,tree);
    }

    public Node translate(ASTVariableDeclarator node,TranslatedTree tree)
    {
        ccbus.tool.parser.typescript.ASTVariableDeclarator nodeResult=
                new ccbus.tool.parser.typescript.ASTVariableDeclarator(AngularParserTreeConstants.JJTVARIABLEDECLARATOR);
        tree.add(nodeResult);

        ASTVariableDeclaratorId variableDeclaratorId=(ASTVariableDeclaratorId)
                node.findNextDownById(JavaParserTreeConstants.JJTVARIABLEDECLARATORID,1);

        ccbus.tool.parser.typescript.ASTVariableDeclaratorId variableDeclaratorIdResult=
                (ccbus.tool.parser.typescript.ASTVariableDeclaratorId)
                tree.translate(JavaParserTreeConstants.JJTVARIABLEDECLARATORID,variableDeclaratorId);

        nodeResult.add(variableDeclaratorIdResult);

        ccbus.tool.parser.java.ASTVariableInitializer variableInitializer=
                (ccbus.tool.parser.java.ASTVariableInitializer)
                node.findFirstDownById(JavaParserTreeConstants.JJTVARIABLEINITIALIZER,1);

        if(null!=variableInitializer)
        {
            SimpleNode variableInitializerResult =
                    (SimpleNode) tree.translateRecursive(variableInitializer, this);
            nodeResult.add(variableInitializerResult);
        }


        Node fieldDeclaration=node.findFirstUpById(JavaParserTreeConstants.JJTFIELDDECLARATION);

        // type should be fetched by top node, could be field, local parameter
        ASTType typeResult=null;
        if(null!=fieldDeclaration)
        {
            Node type=fieldDeclaration.findFirstDownById(JavaParserTreeConstants.JJTTYPE);

            typeResult=(ASTType) tree.translate(JavaParserTreeConstants.JJTTYPE,type);
            nodeResult.add(typeResult);
        }

        Node type=node.findFirstDownById(JavaParserTreeConstants.JJTTYPE);

        if(null!=type)
        {
            typeResult = (ASTType) tree.translate(JavaParserTreeConstants.JJTTYPE, type);
            nodeResult.add(typeResult);
        }


        for(int i=1;i<5;i++)
        {
            ASTBracket bracket=(ASTBracket)
                    variableDeclaratorId.findNextDownById(JavaParserTreeConstants.JJTBRACKET,1);

            if(null==bracket)
            {
                break;
            }

            ccbus.tool.parser.typescript.ASTBracket bracketResult=(ccbus.tool.parser.typescript.ASTBracket)
                    tree.translate(JavaParserTreeConstants.JJTBRACKET,bracket);

            if(null!=typeResult)
            {
                typeResult.add(bracketResult);
            }
        }

        return nodeResult;
    }
}
