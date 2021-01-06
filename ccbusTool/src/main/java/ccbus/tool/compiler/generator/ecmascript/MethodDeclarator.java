package ccbus.tool.compiler.generator.ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.ASTFormalParameters;
import ccbus.tool.parser.ecmascript.ASTTypeParameters;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;

public class MethodDeclarator extends CodeGenerator {

    public MethodDeclarator(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        this.emit(node.findFirstDownById(EcmaParserTreeConstants.JJTIDENTIFIER));
        ASTTypeParameters typeParameters=
                (ASTTypeParameters)
                node.findNextDownById(EcmaParserTreeConstants.JJTTYPEPARAMETERS,1);

        if(null!=typeParameters)
        {
            (new TypeParameters(this)).generate(typeParameters);
        }

        ASTFormalParameters formalParameters=
                (ASTFormalParameters)
                        node.findNextDownById(EcmaParserTreeConstants.JJTFORMALPARAMETERS,1);

        if(null!=formalParameters)
        {
            (new FormalParameters(this)).generate(formalParameters);
        }

    }
}
