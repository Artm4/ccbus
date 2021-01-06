package ccbus.tool.compiler.generator.typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.*;

public class MethodDeclaration extends CodeGenerator{

    public MethodDeclaration(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node)
    {
        MethodDeclarator methodDeclaratorGen=new MethodDeclarator(this);
        methodDeclaratorGen.generate(
                node.findFirstDownById(AngularParserTreeConstants.JJTMETHODDECLARATOR,1));

        this.emitSpace();
        this.emitKind(AngularParserConstants.COLON);
        this.emitSpace();
        SimpleNode voidType=
                (SimpleNode) node.findFirstDownById(AngularParserTreeConstants.JJTVOIDTYPE,2);
        if(null!=voidType)
        {
            this.emit(voidType);
        }
        ASTType type=
                (ASTType) node.findFirstDownById(AngularParserTreeConstants.JJTTYPE,2);
        if(null!=type)
        {
            (new Type(this)).generate(type);
        }

        Block blockGen=new Block(this);
        ASTBlock block=(ASTBlock) node.findFirstDownById(AngularParserTreeConstants.JJTBLOCK,1);
        if(null!=block) {
            //this.emitLn();
            blockGen.generate(block);
        }

        SimpleNode semicolon=(SimpleNode) node.findFirstDownById(AngularParserTreeConstants.JJTSEMICOLONTOKEN,1);
        if(null!=semicolon)
        {
            this.emit(semicolon);
        }
    }
}
