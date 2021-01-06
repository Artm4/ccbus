package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.ASTClassOrInterfaceBodyDeclaration;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.parser.java.*;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.parser.ecmascript.ASTIdentifier;

public class ClassBodyDeclaration implements TreeTranslator
{
    public Node translate(Node node, TranslatedTree tree)
    {
        ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclarationResult=
                new ASTClassOrInterfaceBodyDeclaration(EcmaParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION);
        tree.add(classOrInterfaceBodyDeclarationResult);

        ASTFieldDeclaration fieldDeclaration=(ASTFieldDeclaration) node
                .findFirstDownById(JavaParserTreeConstants.JJTFIELDDECLARATION,1);

        ASTModifiers modifiers=(ASTModifiers) node.findFirstDownById(JavaParserTreeConstants.JJTMODIFIERS,1);
        ccbus.tool.parser.ecmascript.ASTModifiers modifiersResult=null;
        if(null!=modifiers)
        {
            modifiersResult=
                    (ccbus.tool.parser.ecmascript.ASTModifiers)
                    tree.translate(JavaParserTreeConstants.JJTMODIFIERS,modifiers);

            classOrInterfaceBodyDeclarationResult.add(modifiersResult);
        }

        if(null!=fieldDeclaration)
        {
            Node fieldDeclarationList=tree.translate(JavaParserTreeConstants.JJTFIELDDECLARATION,fieldDeclaration);
            for(int i=0;i<fieldDeclarationList.jjtGetNumChildren();i++)
            {
                ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclarationResultItem=
                        new ASTClassOrInterfaceBodyDeclaration(EcmaParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION);
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
            ccbus.tool.parser.ecmascript.ASTMethodDeclaration methodDeclarationResult;

            methodDeclarationResult =
                    (ccbus.tool.parser.ecmascript.ASTMethodDeclaration)
                            tree.translate(JavaParserTreeConstants.JJTMETHODDECLARATION, methodDeclaration);


            ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclarationResultItem=
                    new ASTClassOrInterfaceBodyDeclaration(EcmaParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION);
            if(null!=modifiersResult) {
                classOrInterfaceBodyDeclarationResultItem.add(modifiersResult);
            }

            // Check if prefix render do not translate it. Purpose it to be used only for client rendering
            ASTIdentifier identifier=(ASTIdentifier)
                    methodDeclarationResult.findFirstDownById(EcmaParserTreeConstants.JJTIDENTIFIER,2);
            if(!identifier.image().startsWith("render"))
            {
                classOrInterfaceBodyDeclarationResultItem.add(methodDeclarationResult);
                tree.translatedNode().jjtGetParent().getNode().add(classOrInterfaceBodyDeclarationResultItem);
            }
            return classOrInterfaceBodyDeclarationResult;
        }

        return classOrInterfaceBodyDeclarationResult;
    }
}
