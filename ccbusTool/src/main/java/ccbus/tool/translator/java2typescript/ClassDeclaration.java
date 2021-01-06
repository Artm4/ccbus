package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.*;
import ccbus.tool.parser.java.JavaParserTreeConstants;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.TranslatedTree;

public class ClassDeclaration implements TreeTranslator
{
    public Node translate(Node node, TranslatedTree tree) {
        ASTClassOrInterfaceDeclaration classOrInterfaceDeclarationResult=
                new ASTClassOrInterfaceDeclaration(AngularParserTreeConstants.JJTCLASSORINTERFACEDECLARATION);
        tree.add(classOrInterfaceDeclarationResult);

        ASTIdentifier nodeIdResult=(ASTIdentifier)tree.translate(JavaParserTreeConstants.JJTIDENTIFIER,
                node.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,2)
        );

        ASTClassOrInterfaceBody classBodyResult=(ASTClassOrInterfaceBody)tree.translate(JavaParserTreeConstants.JJTCLASSORINTERFACEBODY,
                node.findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACEBODY,2)
        );


        ccbus.tool.parser.java.ASTTypeIdentifier typeIdentifier=(ccbus.tool.parser.java.ASTTypeIdentifier)
                node.findFirstDownById(JavaParserTreeConstants.JJTTYPEIDENTIFIER,2);

        ASTTypeIdentifier typeIdentifierResult=new ASTTypeIdentifier(AngularParserTreeConstants.JJTTYPEIDENTIFIER);
        typeIdentifierResult.jjtSetFirstToken(typeIdentifier.jjtGetFirstToken());

        classOrInterfaceDeclarationResult.add(typeIdentifierResult);
        classOrInterfaceDeclarationResult.add(nodeIdResult);
        classOrInterfaceDeclarationResult.add(classBodyResult);

        ccbus.tool.parser.java.ASTTypeParameters typeParameters=
                (ccbus.tool.parser.java.ASTTypeParameters )
                        node.findFirstDownById(JavaParserTreeConstants.JJTTYPEPARAMETERS,2);
        if(typeParameters!=null) {
            ASTTypeParameters typeParametersResult = (ASTTypeParameters) tree.translate(JavaParserTreeConstants.JJTTYPEPARAMETERS,
                    typeParameters
            );
            classOrInterfaceDeclarationResult.add(typeParametersResult);
        }


        return classOrInterfaceDeclarationResult;
    }
}
