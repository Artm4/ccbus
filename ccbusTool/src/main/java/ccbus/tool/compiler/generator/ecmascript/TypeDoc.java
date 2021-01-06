package ccbus.tool.compiler.generator.ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.ASTClassOrInterfaceType;
import ccbus.tool.parser.ecmascript.ASTType;
import ccbus.tool.parser.ecmascript.EcmaParserConstants;

public class TypeDoc extends CodeGenerator
{
    public TypeDoc(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {this.generate((ASTType)node);}

    public void generate(ASTType node) {
        this.emitLn();
        this.emitKind(EcmaParserConstants.SLASH);
        this.emitKind(EcmaParserConstants.STAR);
        this.emitKind(EcmaParserConstants.STAR);
        this.emitLn();
        this.emitKind(EcmaParserConstants.STAR);
        this.emitSpace();
        this.emitKind(EcmaParserConstants.AT);
        this.emit("type");
        this.emitSpace();
        this.emitKind(EcmaParserConstants.LBRACE);

        Type typeGen=new Type(this);
        typeGen.generate(
                node
        );
        this.emitKind(EcmaParserConstants.RBRACE);

        this.emitLn();
        this.emitKind(EcmaParserConstants.STAR);
        this.emitKind(EcmaParserConstants.SLASH);
        this.emitSpace();
        this.emitLn();
    }

    public void generate(ASTClassOrInterfaceType node,String jsAnnotation) {
        this.emitLn();
        this.emitKind(EcmaParserConstants.SLASH);
        this.emitKind(EcmaParserConstants.STAR);
        this.emitKind(EcmaParserConstants.STAR);
        this.emitLn();
        this.emitKind(EcmaParserConstants.STAR);
        this.emitSpace();
        this.emitKind(EcmaParserConstants.AT);
        this.emit(jsAnnotation);
        this.emitSpace();
        this.emitKind(EcmaParserConstants.LBRACE);

        ClassOrInterfaceType typeGen=new ClassOrInterfaceType(this);
        typeGen.generate(
                node
        );
        this.emitKind(EcmaParserConstants.RBRACE);

        this.emitLn();
        this.emitKind(EcmaParserConstants.STAR);
        this.emitKind(EcmaParserConstants.SLASH);
        this.emitSpace();
        this.emitLn();
    }

    public void generate(ASTClassOrInterfaceType node) {
        this.generate(node,"type");
    }
}
