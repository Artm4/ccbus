package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.intermediate.SimpleNode;
import ccbus.tool.parser.ecmascript.ASTType;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.parser.java.ASTBracket;
import ccbus.tool.parser.java.ASTVariableDeclarator;
import ccbus.tool.parser.java.ASTVariableDeclaratorId;
import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.RecursiveHostTranslator;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;

public class VariableDeclarator  extends GenericTreeTranslator implements TreeTranslator ,RecursiveHostTranslator
{
    public Node translate(Node node, TranslatedTree tree)
    {
        return translate((ASTVariableDeclarator) node,tree);
    }

    public Node translate(ASTVariableDeclarator node,TranslatedTree tree)
    {
        ccbus.tool.parser.ecmascript.ASTVariableDeclarator nodeResult=
                new ccbus.tool.parser.ecmascript.ASTVariableDeclarator(EcmaParserTreeConstants.JJTVARIABLEDECLARATOR);
        tree.add(nodeResult);

        ASTVariableDeclaratorId variableDeclaratorId=(ASTVariableDeclaratorId)
                node.findNextDownById(JavaParserTreeConstants.JJTVARIABLEDECLARATORID,1);

        ccbus.tool.parser.ecmascript.ASTVariableDeclaratorId variableDeclaratorIdResult=
                (ccbus.tool.parser.ecmascript.ASTVariableDeclaratorId)
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

        Node localVariableDeclaration=node.findFirstUpById(JavaParserTreeConstants.JJTLOCALVARIABLEDECLARATION);

        if(null!=localVariableDeclaration)
        {
            Node type=localVariableDeclaration.findFirstDownById(JavaParserTreeConstants.JJTTYPE);
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

            ccbus.tool.parser.ecmascript.ASTBracket bracketResult=(ccbus.tool.parser.ecmascript.ASTBracket)
                    tree.translate(JavaParserTreeConstants.JJTBRACKET,bracket);

            if(null!=typeResult)
            {
                typeResult.add(bracketResult);
            }
        }

        return nodeResult;
    }
}
