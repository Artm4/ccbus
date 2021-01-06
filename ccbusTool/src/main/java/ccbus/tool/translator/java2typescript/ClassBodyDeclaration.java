package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.ASTClassOrInterfaceBodyDeclaration;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;
import ccbus.tool.parser.java.*;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.TranslatedTree;

public class ClassBodyDeclaration implements TreeTranslator
{
    public Node translate(Node node, TranslatedTree tree)
    {
        ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclarationResult=
                new ASTClassOrInterfaceBodyDeclaration(AngularParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION);
        tree.add(classOrInterfaceBodyDeclarationResult);

        ASTFieldDeclaration fieldDeclaration=(ASTFieldDeclaration) node
                .findFirstDownById(JavaParserTreeConstants.JJTFIELDDECLARATION,1);

        ASTModifiers modifiers=(ASTModifiers) node.findFirstDownById(JavaParserTreeConstants.JJTMODIFIERS,1);
        ccbus.tool.parser.typescript.ASTModifiers modifiersResult=null;
        if(null!=modifiers)
        {
            modifiersResult=
                    (ccbus.tool.parser.typescript.ASTModifiers)
                    tree.translate(JavaParserTreeConstants.JJTMODIFIERS,modifiers);

            classOrInterfaceBodyDeclarationResult.add(modifiersResult);
        }

        if(null!=fieldDeclaration)
        {
            Node fieldDeclarationList=tree.translate(JavaParserTreeConstants.JJTFIELDDECLARATION,fieldDeclaration);
            for(int i=0;i<fieldDeclarationList.jjtGetNumChildren();i++)
            {
                ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclarationResultItem=
                        new ASTClassOrInterfaceBodyDeclaration(AngularParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION);
                if(null!=modifiersResult) {
                    classOrInterfaceBodyDeclarationResultItem.add(modifiersResult);
                }
                classOrInterfaceBodyDeclarationResultItem.add(fieldDeclarationList.jjtGetChild(i));
                tree.translatedNode().jjtGetParent().getNode().add(classOrInterfaceBodyDeclarationResultItem);
            }
            return classOrInterfaceBodyDeclarationResult;
        }

        ASTMethodDeclaration methodDeclaration=(ASTMethodDeclaration) node
                .findNextDownById(JavaParserTreeConstants.JJTMETHODDECLARATION,2);

        if(null!=methodDeclaration)
        {
            ccbus.tool.parser.typescript.ASTMethodDeclaration methodDeclarationResult=
                    (ccbus.tool.parser.typescript.ASTMethodDeclaration)
                            tree.translate(JavaParserTreeConstants.JJTMETHODDECLARATION,methodDeclaration);

            ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclarationResultItem=
                    new ASTClassOrInterfaceBodyDeclaration(AngularParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION);
            if(null!=modifiersResult) {
                classOrInterfaceBodyDeclarationResultItem.add(modifiersResult);
            }

            classOrInterfaceBodyDeclarationResultItem.add(methodDeclarationResult);
            tree.translatedNode().jjtGetParent().getNode().add(classOrInterfaceBodyDeclarationResultItem);
            return classOrInterfaceBodyDeclarationResult;
        }

        return classOrInterfaceBodyDeclarationResult;
    }
}
