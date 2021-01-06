package ccbus.tool.compiler.generator.java;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.java.ASTClassOrInterfaceBodyDeclaration;
import ccbus.tool.parser.java.JavaParserConstants;
import ccbus.tool.parser.java.JavaParserTreeConstants;

public class ClassBody extends CodeGenerator
{
    public ClassBody(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node)
    {
        emitLn();
        emitKind(JavaParserConstants.LBRACE);
        indentAdd();
        for(int i=0;i<500;i++)
        {
            ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclaration=(ASTClassOrInterfaceBodyDeclaration)
            node.findNextDownById(JavaParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION,1);
            if(null==classOrInterfaceBodyDeclaration){break;}
            ClassBodyDeclaration classOrInterfaceBodyDeclarationGen=new ClassBodyDeclaration(this);
            classOrInterfaceBodyDeclarationGen.generate(classOrInterfaceBodyDeclaration);
        }

        indentRemove();
        emitLn();
        emitKind(JavaParserConstants.RBRACE);
    }
}
