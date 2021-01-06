package ccbus.tool.compiler.generator.typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.AngularParserConstants;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;

public class ForStatementRange extends CodeGenerator {

    public ForStatementRange(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        this.emitKind(AngularParserConstants.LET);
        this.emitSpace();
        this.emit(node.findFirstDownById(AngularParserTreeConstants.JJTIDENTIFIER));
        this.emitSpace();
        this.emitKind(AngularParserConstants.COLON);
        this.emitSpace();
        (new Type(this)).generate(
                node.findFirstDownById(AngularParserTreeConstants.JJTTYPE)
        );
        this.emitSpace();
        this.emitKind(AngularParserConstants.OF);
        this.emitSpace();
        this.recursiveGenerate(node.findFirstDownById(AngularParserTreeConstants.JJTEXPRESSION));
    }
}
