package ccbus.tool.compiler.generator.java;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.java.*;


public class ClassBodyDeclaration extends CodeGenerator
{
    public ClassBodyDeclaration(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node)
    {
//       Modifiers not emitted right. Better emit public access.

//        ASTModifiers modifiers=(ASTModifiers)
//                node.findNextDownById(JavaParserTreeConstants.JJTMODIFIERS,1);
//        if(null!=modifiers && modifiers.jjtGetNumChildren()>0)
//        {
//            emitLn();
//            Modifiers modifiersGen=new Modifiers(this);
//            modifiersGen.generate(modifiers);
//            this.emitSpace();
//        }

        emitLn();
        this.emitKind(JavaParserConstants.PUBLIC);
        this.emitSpace();

        for(int i=0;i<node.jjtGetNumChildren();i++)
        {
            Node n=node.jjtGetChild(i);
            if(n.getId()==JavaParserTreeConstants.JJTFIELDDECLARATION)
            {
                Node fieldDeclaration=n;
                if(null!=fieldDeclaration){
                    ASTType type=(ASTType)
                            fieldDeclaration.findFirstDownById(JavaParserTreeConstants.JJTTYPE,1);

                    ASTVariableDeclaratorId variableDeclaratorId=(ASTVariableDeclaratorId)
                            fieldDeclaration.findFirstDownById(JavaParserTreeConstants.JJTVARIABLEDECLARATORID,2);


                    emitTokenList(type);
                    emitTokenList(variableDeclaratorId);
                    emitKind(JavaParserConstants.SEMICOLON);
                }
            }
            else
            if(n.getId()==JavaParserTreeConstants.JJTMETHODDECLARATION)
            {
                Node methodDeclaration=n;
                this.emitTokenList(methodDeclaration);
                emitLn();
            }
            else
            if(n.getId()==JavaParserTreeConstants.JJTMODIFIERS)
            {

                for(int j=0;j<n.jjtGetNumChildren();j++)
                {
                    Node m=n.jjtGetChild(j);
                    if(m.jjtGetFirstToken().isKind(JavaParserConstants.STATIC))
                    {
                        this.emitTokenList(m);
                    }
                }
            }
            else
            {
                this.emitTokenList(n);
                emitLn();
            }
        }

//        ASTFieldDeclaration fieldDeclaration=(ASTFieldDeclaration)
//                node.findNextDownById(JavaParserTreeConstants.JJTFIELDDECLARATION,1);
//        if(null!=fieldDeclaration){
//            ASTType type=(ASTType)
//                    fieldDeclaration.findFirstDownById(JavaParserTreeConstants.JJTTYPE,1);
//
//            ASTVariableDeclaratorId variableDeclaratorId=(ASTVariableDeclaratorId)
//                    fieldDeclaration.findFirstDownById(JavaParserTreeConstants.JJTVARIABLEDECLARATORID,2);
//
//
//            emitTokenList(type);
//            emitTokenList(variableDeclaratorId);
//            emitKind(JavaParserConstants.SEMICOLON);
//        }
//
//        ASTMethodDeclaration methodDeclaration=(ASTMethodDeclaration)
//                node.findNextDownById(JavaParserTreeConstants.JJTMETHODDECLARATION,1);
//        if(null!=methodDeclaration){
//            this.emitTokenList(methodDeclaration);
//            emitLn();
//        }
    }
}
