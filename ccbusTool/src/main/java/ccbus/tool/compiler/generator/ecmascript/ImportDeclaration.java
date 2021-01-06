package ccbus.tool.compiler.generator.ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.*;

public class ImportDeclaration extends CodeGenerator {

    public ImportDeclaration(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        this.emitKind(EcmaParserConstants.IMPORT);
        this.emitSpace();
        ASTImportDefault importDefault=
                (ASTImportDefault)
                node.findFirstDownById(EcmaParserTreeConstants.JJTIMPORTDEFAULT,1);
        if(null!=importDefault)
        {
            this.emit(importDefault.findLeaf().image());
        }

        ASTImportNamed importNamed=
                (ASTImportNamed)
                        node.findFirstDownById(EcmaParserTreeConstants.JJTIMPORTNAMED,1);
        if(null!=importNamed)
        {
            if(null!=importDefault)
            {
                this.emitKind(EcmaParserConstants.COMMA);
                this.emitSpace();
            }

            ASTIdentifier identifier=(ASTIdentifier)
                    importNamed.findNextDownById(EcmaParserTreeConstants.JJTIDENTIFIER,1);
            boolean brace=null!=identifier;

            if(brace)
            {
                this.emitKind(EcmaParserConstants.LBRACE);
            }

            while(null!=identifier)
            {
                this.emit(identifier);
                identifier=(ASTIdentifier)
                        importNamed.findNextDownById(EcmaParserTreeConstants.JJTIDENTIFIER,1);
                if(null!=identifier)
                {
                    this.emitKind(EcmaParserConstants.COMMA);
                    this.emitSpace();
                }
            }

            if(brace)
            {
                this.emitKind(EcmaParserConstants.RBRACE);
            }

        }

        this.emitSpace();
        this.emitKind(EcmaParserConstants.FROM);
        this.emitSpace();
        this.emitKind(EcmaParserConstants.SINGLE_QUOTE);
        this.emit(node.findFirstDownById(EcmaParserTreeConstants.JJTSTRINGLITERAL,1));
        this.emitKind(EcmaParserConstants.SINGLE_QUOTE);
        this.emitKind(EcmaParserConstants.SEMICOLON);
    }
}
