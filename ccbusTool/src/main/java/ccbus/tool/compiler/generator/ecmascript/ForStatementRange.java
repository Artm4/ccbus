package ccbus.tool.compiler.generator.ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.EcmaParserConstants;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;

public class ForStatementRange extends CodeGenerator {

    public ForStatementRange(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
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

        (new Type(this)).generate(
                node.findFirstDownById(EcmaParserTreeConstants.JJTTYPE)
        );

        this.emitKind(EcmaParserConstants.RBRACE);

        this.emitLn();
        this.emitKind(EcmaParserConstants.STAR);
        this.emitKind(EcmaParserConstants.SLASH);
        this.emitSpace();
        this.emitLn();

        this.emitKind(EcmaParserConstants.LET);
        this.emitSpace();
        this.emit(node.findFirstDownById(EcmaParserTreeConstants.JJTIDENTIFIER));

        this.emitSpace();
        this.emitKind(EcmaParserConstants.OF);
        this.emitSpace();
        this.recursiveGenerate(node.findFirstDownById(EcmaParserTreeConstants.JJTEXPRESSION));
    }
}
