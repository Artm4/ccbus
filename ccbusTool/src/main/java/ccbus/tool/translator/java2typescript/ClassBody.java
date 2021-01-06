package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.java.*;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.TranslatedTree;

public class ClassBody implements TreeTranslator
{
    public Node translate(Node node , TranslatedTree tree)
    {
        ccbus.tool.parser.typescript.ASTClassOrInterfaceBody classOrInterfaceBodyResult=
                new ccbus.tool.parser.typescript.ASTClassOrInterfaceBody(AngularParserTreeConstants.JJTCLASSORINTERFACEBODY);
        tree.add(classOrInterfaceBodyResult);

        for(int i=0;i<500;i++)
        {
            ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclaration=
                    (ASTClassOrInterfaceBodyDeclaration)
                    node.findNextDownById(JavaParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION,2);

            if(null==classOrInterfaceBodyDeclaration){break;}
            tree.translate(JavaParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION,
                    classOrInterfaceBodyDeclaration);
        }
        return classOrInterfaceBodyResult;
    }
}
