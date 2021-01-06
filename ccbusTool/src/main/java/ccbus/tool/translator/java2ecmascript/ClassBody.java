package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.parser.java.ASTClassOrInterfaceBodyDeclaration;
import ccbus.tool.parser.java.ASTConstructorDeclaration;
import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.util.java.Tool;

public class ClassBody implements TreeTranslator
{
    public Node translate(Node node , TranslatedTree tree)
    {
        ((Tool)tree.tool()).symTabStack().push(node.symTab());
        ccbus.tool.parser.ecmascript.ASTClassOrInterfaceBody classOrInterfaceBodyResult=
                new ccbus.tool.parser.ecmascript.ASTClassOrInterfaceBody(EcmaParserTreeConstants.JJTCLASSORINTERFACEBODY);
        tree.add(classOrInterfaceBodyResult);

        // Translate constructor
        ASTConstructorDeclaration constructorDeclaration=(ASTConstructorDeclaration) node
                .findFirstDownById(JavaParserTreeConstants.JJTCONSTRUCTORDECLARATION,3);
        if(null!=constructorDeclaration)
        {
            ccbus.tool.parser.ecmascript.ASTClassOrInterfaceBodyDeclaration constructorDeclarationResult=
                    (ccbus.tool.parser.ecmascript.ASTClassOrInterfaceBodyDeclaration)
                            tree.translate(JavaParserTreeConstants.JJTCONSTRUCTORDECLARATION, node);

            classOrInterfaceBodyResult.add(constructorDeclarationResult);

        }


        for(int i=0;i<500;i++)
        {
            ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclaration=
                    (ASTClassOrInterfaceBodyDeclaration)
                    node.findNextDownById(JavaParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION,2);

            if(null==classOrInterfaceBodyDeclaration){break;}
            tree.translate(JavaParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION,
                    classOrInterfaceBodyDeclaration);
        }
        ((Tool)tree.tool()).symTabStack().pop();
        return classOrInterfaceBodyResult;
    }
}
