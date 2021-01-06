package ccbus.tool.compiler.generator.ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.*;
import ccbus.tool.intermediate.SimpleNode;

public class LiteralDeclaration  extends CodeGenerator {
    public LiteralDeclaration(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node)
    {
        this.emitKind(EcmaParserConstants.LBRACE);
        this.indentAdd();
        this.emitLn();
        ASTLiteralField field=(ASTLiteralField)
                node.findNextDownById(EcmaParserTreeConstants.JJTLITERALFIELD,1);
        while(null!=field)
        {
            ASTType type=(ASTType)
                    field.findNextDownById(EcmaParserTreeConstants.JJTTYPE,1);
            if(null!=type)
            {
                (new TypeDoc(this)).generate(type);
            }

            ASTVariableDeclaratorId variableDeclaratorId=(ASTVariableDeclaratorId)
                    field.findFirstDownById(EcmaParserTreeConstants.JJTVARIABLEDECLARATORID,1);
            SimpleNode variableInitializer=(SimpleNode)
                    field.findFirstDownById(EcmaParserTreeConstants.JJTVARIABLEINITIALIZER,1);
            this.emit(variableDeclaratorId.findLeaf());
            if(null!=variableInitializer)
            {
                this.emitKind(EcmaParserConstants.COLON);
                emitSpace();
                this.recursiveGenerate(variableInitializer);
            }

            ASTLiteralMethod literalMethod=(ASTLiteralMethod)
                    field.findFirstDownById(EcmaParserTreeConstants.JJTLITERALMETHOD,1);
            if(null!=literalMethod)
            {
                this.translateMethod(literalMethod);
            }

            field=(ASTLiteralField)
                    node.findNextDownById(EcmaParserTreeConstants.JJTLITERALFIELD,1);
            if(null!=field)
            {
                this.emitKind(EcmaParserConstants.COMMA);
                this.emitLn();
            }
        }

        this.indentRemove();
        this.emitLn();
        this.emitKind(EcmaParserConstants.RBRACE);
    }

    private void translateMethod(ASTLiteralMethod literalMethod)
    {
        this.emitKind(EcmaParserConstants.COLON);
        emitSpace();
        emitKind(EcmaParserConstants.FUNCTION);
        ASTFormalParameters formalParameters=(ASTFormalParameters)
                literalMethod.findFirstDownById(EcmaParserTreeConstants.JJTFORMALPARAMETERS,1);
        (new FormalParameters(this)).generate(formalParameters);

        ASTBlock block=(ASTBlock)
                literalMethod.findFirstDownById(EcmaParserTreeConstants.JJTBLOCK,1);
        (new Block(this)).generate(block);
    }
}
