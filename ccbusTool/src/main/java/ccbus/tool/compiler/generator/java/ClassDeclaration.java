package ccbus.tool.compiler.generator.java;

import ccbus.tool.intermediate.Node;
import ccbus.tool.intermediate.SimpleNode;
import ccbus.tool.parser.java.*;

public class ClassDeclaration extends CodeGenerator
{
    public ClassDeclaration(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node)
    {
        ASTTypeDeclaration typeDeclaration=(ASTTypeDeclaration)node;
        ASTModifiers modifiers=(ASTModifiers)
                typeDeclaration.findFirstDownById(JavaParserTreeConstants.JJTMODIFIERS,1);
        ASTClassOrInterfaceDeclaration classDeclaration=(ASTClassOrInterfaceDeclaration)
                typeDeclaration.findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACEDECLARATION,1);
        ASTClassOrInterfaceBody classBody=(ASTClassOrInterfaceBody)
                classDeclaration.findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACEBODY,2);


        Modifiers modifiersGen=new Modifiers(this);
        modifiersGen.generate(modifiers);

        emitSpace();
        emit(classDeclaration.jjtGetChild(0));
        emitSpace();
        emit(classDeclaration.jjtGetChild(1));

        SimpleNode extendsList=(SimpleNode)
                node.findFirstDownById(JavaParserTreeConstants.JJTEXTENDSLIST,3);
        if(null!=extendsList)
        {
            emitSpace();
            emitKind(JavaParserConstants.EXTENDS);
            emitSpace();
            ASTClassOrInterfaceType classType=(ASTClassOrInterfaceType)
                    extendsList.findNextDownById(JavaParserTreeConstants.JJTCLASSORINTERFACETYPE,1);

            ClassOrInterfaceType classOrInterfaceTypeGenerator=new ClassOrInterfaceType(this);
            classOrInterfaceTypeGenerator.generate(classType);
        }

        ClassBody classBodyGen=new ClassBody(this);
        classBodyGen.generate(classBody);
    }
}
