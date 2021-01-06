package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.*;
import ccbus.tool.parser.java.JavaParserTreeConstants;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.TranslatedTree;

public class FormalParameter implements TreeTranslator
{
    public Node translate(Node node, TranslatedTree tree)
    {
        ASTFormalParameter nodeResult=
                new ASTFormalParameter(AngularParserTreeConstants.JJTFORMALPARAMETER);
        tree.add(nodeResult);

        ccbus.tool.parser.java.ASTVarParameters varParameters=
                (ccbus.tool.parser.java.ASTVarParameters)
            node.findFirstDownById(JavaParserTreeConstants.JJTVARPARAMETERS,1);

        if(null!=varParameters)
        {
            ASTVarParameters varParametersResult =
                    (ASTVarParameters)
                            tree.translateSingleToken(varParameters, AngularParserTreeConstants.JJTVARPARAMETERS);
            nodeResult.add(varParametersResult);
        }

        ASTVariableDeclarator variableDeclaratorResult=(ASTVariableDeclarator)
                new ASTVariableDeclarator(AngularParserTreeConstants.JJTVARIABLEDECLARATOR);

        Node type=node.findFirstDownById(JavaParserTreeConstants.JJTTYPE);

        ASTType typeResult=(ASTType) tree.translate(JavaParserTreeConstants.JJTTYPE,type);

        ccbus.tool.parser.java.ASTVariableDeclaratorId variableDeclaratorId=(ccbus.tool.parser.java.ASTVariableDeclaratorId)
                node.findNextDownById(JavaParserTreeConstants.JJTVARIABLEDECLARATORID,1);

        ccbus.tool.parser.typescript.ASTVariableDeclaratorId variableDeclaratorIdResult=
                (ccbus.tool.parser.typescript.ASTVariableDeclaratorId)
                        tree.translate(JavaParserTreeConstants.JJTVARIABLEDECLARATORID,variableDeclaratorId);
        variableDeclaratorResult.add(variableDeclaratorIdResult);

        for(int i=1;i<5;i++)
        {
            ccbus.tool.parser.java.ASTBracket bracket=(ccbus.tool.parser.java.ASTBracket)
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

        variableDeclaratorResult.add(typeResult);
        nodeResult.add(variableDeclaratorResult);

        return nodeResult;
    }
}
