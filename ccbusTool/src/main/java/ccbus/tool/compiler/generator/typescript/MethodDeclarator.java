package ccbus.tool.compiler.generator.typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.ASTFormalParameters;
import ccbus.tool.parser.typescript.ASTTypeParameters;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;

public class MethodDeclarator extends CodeGenerator {

    public MethodDeclarator(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        this.emit(node.findFirstDownById(AngularParserTreeConstants.JJTIDENTIFIER));
        ASTTypeParameters typeParameters=
                (ASTTypeParameters)
                node.findNextDownById(AngularParserTreeConstants.JJTTYPEPARAMETERS,1);

        if(null!=typeParameters)
        {
            (new TypeParameters(this)).generate(typeParameters);
        }

        ASTFormalParameters formalParameters=
                (ASTFormalParameters)
                        node.findNextDownById(AngularParserTreeConstants.JJTFORMALPARAMETERS,1);

        if(null!=formalParameters)
        {
            (new FormalParameters(this)).generate(formalParameters);
        }

    }
}
