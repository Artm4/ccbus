package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.parser.java.ASTFieldDeclaration;
import ccbus.tool.parser.java.ASTVariableDeclarator;
import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;

public class FieldDeclaration implements TreeTranslator
{
    public Node translate(Node node,TranslatedTree tree)
    {
        return this.translate((ASTFieldDeclaration)node,tree);
    }

    public Node translate(ASTFieldDeclaration node, TranslatedTree tree)
    {
        ccbus.tool.parser.ecmascript.ASTFieldDeclaration nodeResultList=
                new ccbus.tool.parser.ecmascript.ASTFieldDeclaration(EcmaParserTreeConstants.JJTFIELDDECLARATION);
        tree.add(nodeResultList);

        for(int i=0;i<10;i++)
        {
            ASTVariableDeclarator variableDeclarator=(ASTVariableDeclarator)
                node.findNextDownById(JavaParserTreeConstants.JJTVARIABLEDECLARATOR,1);

            if(null!=variableDeclarator)
            {
                ccbus.tool.parser.ecmascript.ASTVariableDeclarator variableDeclaratorResult=
                    (ccbus.tool.parser.ecmascript.ASTVariableDeclarator )
                    tree.translate(JavaParserTreeConstants.JJTVARIABLEDECLARATOR,variableDeclarator);

                ccbus.tool.parser.ecmascript.ASTFieldDeclaration nodeResultField=
                        new ccbus.tool.parser.ecmascript.ASTFieldDeclaration(EcmaParserTreeConstants.JJTFIELDDECLARATION);
                nodeResultField.add(variableDeclaratorResult);
                nodeResultList.add(nodeResultField);
            }
        }
        return nodeResultList;
    }
}

