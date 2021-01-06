package ccbus.tool.compiler.generator.ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.ASTFormalParameter;
import ccbus.tool.parser.ecmascript.EcmaParserConstants;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;

public class FormalParameters extends CodeGenerator {

    public FormalParameters(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        this.emitKind(EcmaParserConstants.LPAREN);
        ASTFormalParameter formalParameter=
                (ASTFormalParameter )
                        node.findNextDownById(EcmaParserTreeConstants.JJTFORMALPARAMETER,1);

        FormalParameter formalParameterGen=new FormalParameter(this);
        for(int i=0;null!=formalParameter;i++)
        {
            if(i!=0){this.emitKind(EcmaParserConstants.COMMA);}
            formalParameterGen.generate(formalParameter);
            formalParameter=(ASTFormalParameter)node.findNextDownById(EcmaParserTreeConstants.JJTFORMALPARAMETER,1);
        }
        this.emitKind(EcmaParserConstants.RPAREN);
    }
}