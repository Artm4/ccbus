package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.*;
import ccbus.tool.parser.java.JavaParserTreeConstants;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.TranslatedTree;

public class TypeDeclaration  implements TreeTranslator
{
    public Node translate(Node node, TranslatedTree tree) {
        ASTTypeDeclaration typeDeclarationResult=new ASTTypeDeclaration(AngularParserTreeConstants.JJTTYPEDECLARATION);
        tree.add(typeDeclarationResult);

        ASTModifiers modifiers=(ASTModifiers)tree.translate(JavaParserTreeConstants.JJTMODIFIERS,
                node.findFirstDownById(JavaParserTreeConstants.JJTMODIFIERS,2)
                );

        ASTClassOrInterfaceDeclaration classOrInterfaceDeclaration=
                (ASTClassOrInterfaceDeclaration)
                tree.translate(JavaParserTreeConstants.JJTCLASSORINTERFACEDECLARATION,
                node.findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACEDECLARATION,10)
                );

        typeDeclarationResult.add(modifiers);
        typeDeclarationResult.add(classOrInterfaceDeclaration);

        return typeDeclarationResult;
    }
}
