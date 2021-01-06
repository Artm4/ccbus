package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;
import ccbus.tool.parser.java.ASTFieldDeclaration;
import ccbus.tool.parser.java.ASTVariableDeclarator;
import ccbus.tool.parser.java.JavaParserTreeConstants;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.TranslatedTree;

public class FieldDeclaration implements TreeTranslator
{
    public Node translate(Node node,TranslatedTree tree)
    {
        return this.translate((ASTFieldDeclaration)node,tree);
    }

    public Node translate(ASTFieldDeclaration node, TranslatedTree tree)
    {
        ccbus.tool.parser.typescript.ASTFieldDeclaration nodeResultList=
                new ccbus.tool.parser.typescript.ASTFieldDeclaration(AngularParserTreeConstants.JJTFIELDDECLARATION);
        tree.add(nodeResultList);

        for(int i=0;i<10;i++)
        {
            ASTVariableDeclarator variableDeclarator=(ASTVariableDeclarator)
                node.findNextDownById(JavaParserTreeConstants.JJTVARIABLEDECLARATOR,1);

            if(null!=variableDeclarator)
            {
                ccbus.tool.parser.typescript.ASTVariableDeclarator variableDeclaratorResult=
                    (ccbus.tool.parser.typescript.ASTVariableDeclarator )
                    tree.translate(JavaParserTreeConstants.JJTVARIABLEDECLARATOR,variableDeclarator);

                ccbus.tool.parser.typescript.ASTFieldDeclaration nodeResultField=
                        new ccbus.tool.parser.typescript.ASTFieldDeclaration(AngularParserTreeConstants.JJTFIELDDECLARATION);
                nodeResultField.add(variableDeclaratorResult);
                nodeResultList.add(nodeResultField);
            }
        }
        return nodeResultList;
    }
}

