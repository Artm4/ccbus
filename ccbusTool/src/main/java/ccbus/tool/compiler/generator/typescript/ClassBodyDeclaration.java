package ccbus.tool.compiler.generator.typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.*;

public class ClassBodyDeclaration extends CodeGenerator
{
    public ClassBodyDeclaration(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node)
    {
        emitLn();
        ASTModifiers modifiers=(ASTModifiers)
                node.findNextDownById(AngularParserTreeConstants.JJTMODIFIERS,1);
        if(null!=modifiers && modifiers.jjtGetNumChildren()>0)
        {
            Modifiers modifiersGen=new Modifiers(this);
            modifiersGen.generate(modifiers);
            this.emitSpace();
        }

        ASTFieldDeclaration fieldDeclaration=(ASTFieldDeclaration)
                node.findNextDownById(AngularParserTreeConstants.JJTFIELDDECLARATION,1);
        if(null!=fieldDeclaration){
            FieldDeclaration fieldDeclarationGen=new FieldDeclaration(this);
            fieldDeclarationGen.generate(fieldDeclaration);
        }

        ASTMethodDeclaration methodDeclaration=(ASTMethodDeclaration)
                node.findNextDownById(AngularParserTreeConstants.JJTMETHODDECLARATION,1);
        if(null!=methodDeclaration){
            MethodDeclaration methodDeclarationGen=new MethodDeclaration(this);
            methodDeclarationGen.generate(methodDeclaration);
        }
    }
}
