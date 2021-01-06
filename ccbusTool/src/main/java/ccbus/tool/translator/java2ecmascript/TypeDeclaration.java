package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.ASTClassOrInterfaceDeclaration;
import ccbus.tool.parser.ecmascript.ASTModifiers;
import ccbus.tool.parser.ecmascript.ASTTypeDeclaration;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;

public class TypeDeclaration  implements TreeTranslator
{
    public Node translate(Node node, TranslatedTree tree) {
        ASTTypeDeclaration typeDeclarationResult=new ASTTypeDeclaration(EcmaParserTreeConstants.JJTTYPEDECLARATION);
        tree.add(typeDeclarationResult);

        ASTModifiers modifiers=(ASTModifiers)tree.translate(JavaParserTreeConstants.JJTMODIFIERS,
                node.findFirstDownById(JavaParserTreeConstants.JJTMODIFIERS,1)
                );

        ASTClassOrInterfaceDeclaration classOrInterfaceDeclaration=
                (ASTClassOrInterfaceDeclaration)
                tree.translate(JavaParserTreeConstants.JJTCLASSORINTERFACEDECLARATION,
                node.findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACEDECLARATION,1)
                );

        typeDeclarationResult.add(modifiers);
        typeDeclarationResult.add(classOrInterfaceDeclaration);

        return typeDeclarationResult;
    }
}
