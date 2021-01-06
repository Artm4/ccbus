package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.ASTLocalVariableDeclaration;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;
import ccbus.tool.parser.java.ASTVariableDeclarator;
import ccbus.tool.parser.java.JavaParserTreeConstants;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.TranslatedTree;

public class LocalVariableDeclaration implements TreeTranslator {
    public Node translate(Node node, TranslatedTree tree)
    {
        ASTLocalVariableDeclaration nodeResult=new ASTLocalVariableDeclaration(AngularParserTreeConstants.JJTLOCALVARIABLEDECLARATION);
        tree.add(nodeResult);

        Node type=node.findFirstDownById(JavaParserTreeConstants.JJTTYPE);

        for(int i=0;i<10;i++)
        {
            ASTVariableDeclarator variableDeclarator=(ASTVariableDeclarator)
                    node.findNextDownById(JavaParserTreeConstants.JJTVARIABLEDECLARATOR,1);

            if(null!=variableDeclarator)
            {
                variableDeclarator.add(type);
                ccbus.tool.parser.typescript.ASTVariableDeclarator variableDeclaratorResult=
                        (ccbus.tool.parser.typescript.ASTVariableDeclarator )
                                tree.translate(JavaParserTreeConstants.JJTVARIABLEDECLARATOR,variableDeclarator);

                nodeResult.add(variableDeclaratorResult);
            }
        }

        return nodeResult;
    }
}
