package ccbus.tool.compiler.generator.typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.AngularParserConstants;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;
import ccbus.tool.parser.typescript.ASTFormalParameter;

public class FormalParameters extends CodeGenerator {

    public FormalParameters(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        this.emitKind(AngularParserConstants.LPAREN);
        ASTFormalParameter formalParameter=
                (ASTFormalParameter )
                        node.findNextDownById(AngularParserTreeConstants.JJTFORMALPARAMETER,1);

        FormalParameter formalParameterGen=new FormalParameter(this);
        for(int i=0;null!=formalParameter;i++)
        {
            if(i!=0){this.emitKind(AngularParserConstants.COMMA);}
            formalParameterGen.generate(formalParameter);
            formalParameter=(ASTFormalParameter)node.findNextDownById(AngularParserTreeConstants.JJTFORMALPARAMETER,1);
        }
        this.emitKind(AngularParserConstants.RPAREN);
    }
}