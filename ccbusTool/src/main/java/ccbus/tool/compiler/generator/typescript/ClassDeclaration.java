package ccbus.tool.compiler.generator.typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.*;

public class ClassDeclaration extends CodeGenerator
{
    public ClassDeclaration(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node)
    {
        ASTTypeDeclaration typeDeclaration=(ASTTypeDeclaration)node
                .findNextDownById(AngularParserTreeConstants.JJTTYPEDECLARATION,3);
        ASTModifiers modifiers=(ASTModifiers) typeDeclaration.jjtGetChild(0);
        ASTClassOrInterfaceDeclaration classDeclaration=(ASTClassOrInterfaceDeclaration)typeDeclaration.jjtGetChild(1);
        ASTClassOrInterfaceBody classBody=(ASTClassOrInterfaceBody)classDeclaration.jjtGetChild(2);
        ASTTypeParameters typeParameters=(ASTTypeParameters)
                node.findFirstDownById(AngularParserTreeConstants.JJTTYPEPARAMETERS,2);


        Modifiers modifiersGen=new Modifiers(this);
        modifiersGen.generate(modifiers);

        emitSpace();
        emit(classDeclaration.jjtGetChild(0));
        emitSpace();
        emit(classDeclaration.jjtGetChild(1));

        if(null!=typeParameters) {
            TypeParameters typeParametersGen = new TypeParameters(this);
            typeParametersGen.generate(typeParameters);
        }

        ClassBody classBodyGen=new ClassBody(this);
        classBodyGen.generate(classBody);
    }
}
