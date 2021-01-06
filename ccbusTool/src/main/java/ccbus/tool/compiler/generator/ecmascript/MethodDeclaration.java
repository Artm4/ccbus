package ccbus.tool.compiler.generator.ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.*;

public class MethodDeclaration extends CodeGenerator {

    public MethodDeclaration(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node)
    {
        this.emitLn();
        this.emitKind(EcmaParserConstants.SLASH);
        this.emitKind(EcmaParserConstants.STAR);
        this.emitKind(EcmaParserConstants.STAR);
        this.emitLn();

        ASTFormalParameter formalParameter=
                (ASTFormalParameter )
                        node.findNextDownById(EcmaParserTreeConstants.JJTFORMALPARAMETER,3);

        FormalParameter formalParameterGen=new FormalParameter(this);
        for(int i=0;null!=formalParameter;i++)
        {
            this.emitKind(EcmaParserConstants.STAR);
            this.emitSpace();
            this.emitKind(EcmaParserConstants.AT);
            this.emit("param");
            this.emitSpace();

            this.emitKind(EcmaParserConstants.LBRACE);
            Type typeGen=new Type(this);
            typeGen.generate(
                    formalParameter.findFirstDownById(EcmaParserTreeConstants.JJTTYPE,2)
            );
            this.emitKind(EcmaParserConstants.RBRACE);

            this.emitSpace();
            formalParameterGen.generate(formalParameter);
            formalParameter=(ASTFormalParameter)node.findNextDownById(EcmaParserTreeConstants.JJTFORMALPARAMETER,3);
            this.emitLn();
        }

        this.emitKind(EcmaParserConstants.STAR);
        this.emitSpace();
        this.emitKind(EcmaParserConstants.AT);
        this.emit("returns");
        this.emitSpace();
        this.emitKind(EcmaParserConstants.LBRACE);


        SimpleNode voidType=
                (SimpleNode) node.findFirstDownById(EcmaParserTreeConstants.JJTVOIDTYPE,2);
        if(null!=voidType)
        {
            this.emit(voidType);
        }
        ASTType type=
                (ASTType) node.findFirstDownById(EcmaParserTreeConstants.JJTTYPE,2);
        if(null!=type)
        {
            (new Type(this)).generate(type);
        }

        this.emitKind(EcmaParserConstants.RBRACE);

        this.emitLn();
        this.emitKind(EcmaParserConstants.STAR);
        this.emitKind(EcmaParserConstants.SLASH);
        this.emitSpace();
        this.emitLn();


        MethodDeclarator methodDeclaratorGen=new MethodDeclarator(this);

        methodDeclaratorGen.generate(
                node.findFirstDownById(EcmaParserTreeConstants.JJTMETHODDECLARATOR,1));



        Block blockGen=new Block(this);
        ASTBlock block=(ASTBlock) node.findFirstDownById(EcmaParserTreeConstants.JJTBLOCK,1);
        if(null!=block) {
            //this.emitLn();
            blockGen.generate(block);
        }

        SimpleNode semicolon=(SimpleNode) node.findFirstDownById(EcmaParserTreeConstants.JJTSEMICOLONTOKEN,1);
        if(null!=semicolon)
        {
            this.emit(semicolon);
        }
    }
}
