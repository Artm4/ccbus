package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.intermediate.SimpleNode;
import ccbus.tool.parser.ecmascript.*;
import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.RecursiveHostTranslator;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;

public class ClassDeclaration extends GenericTreeTranslator implements TreeTranslator ,RecursiveHostTranslator
{
    public Node translate(Node node, TranslatedTree tree) {
        ASTClassOrInterfaceDeclaration classOrInterfaceDeclarationResult=
                new ASTClassOrInterfaceDeclaration(EcmaParserTreeConstants.JJTCLASSORINTERFACEDECLARATION);
        tree.add(classOrInterfaceDeclarationResult);

        ASTIdentifier nodeIdResult=(ASTIdentifier)tree.translate(JavaParserTreeConstants.JJTIDENTIFIER,
                node.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,2)
        );

        ASTClassOrInterfaceBody classBodyResult=(ASTClassOrInterfaceBody)tree.translate(JavaParserTreeConstants.JJTCLASSORINTERFACEBODY,
                node.findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACEBODY,2)
        );

        ccbus.tool.parser.java.ASTTypeIdentifier typeIdentifier=(ccbus.tool.parser.java.ASTTypeIdentifier)
                node.findFirstDownById(JavaParserTreeConstants.JJTTYPEIDENTIFIER,2);

        ASTTypeIdentifier typeIdentifierResult=new ASTTypeIdentifier(EcmaParserTreeConstants.JJTTYPEIDENTIFIER);
        typeIdentifierResult.jjtSetFirstToken(typeIdentifier.jjtGetFirstToken());

        ccbus.tool.parser.java.ASTExtendsList extendsList=(ccbus.tool.parser.java.ASTExtendsList)
                node.findFirstDownById(JavaParserTreeConstants.JJTEXTENDSLIST,2);

        classOrInterfaceDeclarationResult.add(typeIdentifierResult);
        classOrInterfaceDeclarationResult.add(nodeIdResult);
        classOrInterfaceDeclarationResult.add(classBodyResult);

        if(null!=extendsList)
        {
            SimpleNode extendsListResult = (SimpleNode) tree.translateRecursive(extendsList, this);
            classOrInterfaceDeclarationResult.add(extendsListResult);
        }

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
