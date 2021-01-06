package ccbus.tool.compiler.generator.ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.EcmaParserConstants;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;

public class LamdaExpression extends CodeGenerator
{

    public LamdaExpression(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node)
    {
        for (int i = 0; i < node.jjtGetNumChildren(); i++)
        {
            if(node.jjtGetChild(i).getId()==EcmaParserTreeConstants.JJTLAMDABODY)
            {
                emitSpace();
                emitKind(EcmaParserConstants.ARROW);
                emitSpace();
                this.recursiveGenerate(node.jjtGetChild(i));
            }
            else
            if(node.jjtGetChild(i).getId()==EcmaParserTreeConstants.JJTFORMALPARAMETERS)
            {
                (new FormalParameters(this)).generate(node.jjtGetChild(i));
            }
        }
    }
}