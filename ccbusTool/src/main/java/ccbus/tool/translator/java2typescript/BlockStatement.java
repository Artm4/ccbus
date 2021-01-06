package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.intermediate.SimpleNode;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;
import ccbus.tool.parser.typescript.ASTBlockStatement;
import ccbus.tool.parser.java.ASTClassOrInterfaceDeclaration;
import ccbus.tool.parser.java.ASTStatement;
import ccbus.tool.parser.java.JavaParserTreeConstants;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.util.java.Tool;

public class BlockStatement implements TreeTranslator {
    public Node translate(Node node, TranslatedTree tree)
    {
        Tool tool=(Tool)tree.tool();
        ASTBlockStatement nodeResult=
                new ASTBlockStatement(AngularParserTreeConstants.JJTBLOCKSTATEMENT);
        tree.add(nodeResult);

        ASTClassOrInterfaceDeclaration classOrInterfaceDeclaration=
                (ASTClassOrInterfaceDeclaration )
                node.findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACEDECLARATION,1);
        if(null!=classOrInterfaceDeclaration)
        {
            //error not allowed inner class
            tool.errorTranslate(classOrInterfaceDeclaration,"Not allowed inner class");
        }

        ASTStatement statement=(ASTStatement)
                node.findFirstDownById(JavaParserTreeConstants.JJTSTATEMENT,1);
        if(null!=statement)
        {
            SimpleNode statementResult=
                    (SimpleNode)
                    tree.translate(JavaParserTreeConstants.JJTSTATEMENT,statement);
            nodeResult.add(statementResult);
        }

        ccbus.tool.parser.java.ASTLocalVariableDeclaration localVariableDeclaration=
                (ccbus.tool.parser.java.ASTLocalVariableDeclaration)
                node.findFirstDownById(JavaParserTreeConstants.JJTLOCALVARIABLEDECLARATION,1);

        if(null!=localVariableDeclaration)
        {
            Node localVariableDeclarationResult=
                    tree.translate(JavaParserTreeConstants.JJTLOCALVARIABLEDECLARATION,
                    localVariableDeclaration);
            nodeResult.add(localVariableDeclarationResult);
        }


        return nodeResult;
    }
}
