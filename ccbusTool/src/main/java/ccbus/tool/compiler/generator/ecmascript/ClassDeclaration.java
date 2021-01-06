package ccbus.tool.compiler.generator.ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.intermediate.SimpleNode;
import ccbus.tool.parser.ecmascript.*;
import ccbus.tool.parser.java.JavaParserTreeConstants;

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
        ASTModifiers modifiers=(ASTModifiers) typeDeclaration.jjtGetChild(0);
        ASTClassOrInterfaceDeclaration classDeclaration=(ASTClassOrInterfaceDeclaration)typeDeclaration.jjtGetChild(1);
        ASTClassOrInterfaceBody classBody=(ASTClassOrInterfaceBody)classDeclaration.jjtGetChild(2);
        ccbus.tool.intermediate.SimpleNode extendsList=(SimpleNode)
                node.findFirstDownById(EcmaParserTreeConstants.JJTEXTENDSLIST,3);

        if(null!=extendsList)
        {
            ASTClassOrInterfaceType extendType=(ASTClassOrInterfaceType)extendsList.findFirstDownById(
                    EcmaParserTreeConstants.JJTCLASSORINTERFACETYPE,4
            );
            new TypeDoc(this).generate(extendType,"extends");
        }

        Modifiers modifiersGen=new Modifiers(this);
        modifiersGen.generate(modifiers);

        emitSpace();
        emit(classDeclaration.jjtGetChild(0));
        emitSpace();
        emit(classDeclaration.jjtGetChild(1));


        if(null!=extendsList)
        {
            emitSpace();
            emitKind(EcmaParserConstants.EXTENDS);
            emitSpace();
            ASTClassOrInterfaceType classType=(ASTClassOrInterfaceType)
                    extendsList.findNextDownById(EcmaParserTreeConstants.JJTCLASSORINTERFACETYPE,1);

            Node identifier = classType.findNextDownById(EcmaParserTreeConstants.JJTIDENTIFIER, 1);
            this.emit(identifier);
        }

        ClassBody classBodyGen=new ClassBody(this);
        classBodyGen.generate(classBody);
    }
}
