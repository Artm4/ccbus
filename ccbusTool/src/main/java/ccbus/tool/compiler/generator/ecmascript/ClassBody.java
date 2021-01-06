package ccbus.tool.compiler.generator.ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.ASTClassOrInterfaceBodyDeclaration;
import ccbus.tool.parser.ecmascript.EcmaParserConstants;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;

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
        emitKind(EcmaParserConstants.LBRACE);
        indentAdd();
        for(int i=0;i<500;i++)
        {
            ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclaration=(ASTClassOrInterfaceBodyDeclaration)
            node.findNextDownById(EcmaParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION,1);
            if(null==classOrInterfaceBodyDeclaration){break;}
            ClassBodyDeclaration classOrInterfaceBodyDeclarationGen=new ClassBodyDeclaration(this);
            classOrInterfaceBodyDeclarationGen.generate(classOrInterfaceBodyDeclaration);
        }

        this.firePostMethodDeclActions();

        indentRemove();
        emitLn();
        emitKind(EcmaParserConstants.RBRACE);
        this.firePostFunctionDeclActions();
    }
}
