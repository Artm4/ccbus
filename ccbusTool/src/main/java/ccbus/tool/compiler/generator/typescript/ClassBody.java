package ccbus.tool.compiler.generator.typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.*;

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
        emitKind(AngularParserConstants.LBRACE);
        indentAdd();
        for(int i=0;i<500;i++)
        {
            ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclaration=(ASTClassOrInterfaceBodyDeclaration)
            node.findNextDownById(AngularParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION,1);
            if(null==classOrInterfaceBodyDeclaration){break;}
            ClassBodyDeclaration classOrInterfaceBodyDeclarationGen=new ClassBodyDeclaration(this);
            classOrInterfaceBodyDeclarationGen.generate(classOrInterfaceBodyDeclaration);
        }

        indentRemove();
        emitLn();
        emitKind(AngularParserConstants.RBRACE);
    }
}
