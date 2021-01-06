package ccbus.tool.compiler.generator.ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.EcmaParserConstants;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;

public class FieldDeclaration extends CodeGenerator {

    public FieldDeclaration(CodeGenerator codeGenerator)
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
        this.emitKind(EcmaParserConstants.STAR);
        this.emitSpace();
        this.emitKind(EcmaParserConstants.AT);
        this.emit("type");
        this.emitSpace();
        this.emitKind(EcmaParserConstants.LBRACE);

        Type typeGen=new Type(this);
        typeGen.generate(
                node.findFirstDownById(EcmaParserTreeConstants.JJTTYPE,2)
        );
        this.emitKind(EcmaParserConstants.RBRACE);

        this.emitLn();
        this.emitKind(EcmaParserConstants.STAR);
        this.emitKind(EcmaParserConstants.SLASH);
        this.emitSpace();
        this.emitLn();


        VariableDeclarator variableDeclaratorGen=new VariableDeclarator(this);
        variableDeclaratorGen.generate(
                node.findFirstDownById(EcmaParserTreeConstants.JJTVARIABLEDECLARATOR,1));
        this.emitKind(EcmaParserConstants.SEMICOLON);
    }
}
