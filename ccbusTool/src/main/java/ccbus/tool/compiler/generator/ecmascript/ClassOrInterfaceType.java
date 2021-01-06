package ccbus.tool.compiler.generator.ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.EcmaParserConstants;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;

public class ClassOrInterfaceType extends CodeGenerator
{
    public ClassOrInterfaceType(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        Node identifier = node.findNextDownById(EcmaParserTreeConstants.JJTIDENTIFIER, 1);
        while(null != identifier)
        {
            this.emit(identifier);
            identifier = node.findNextDownById(EcmaParserTreeConstants.JJTIDENTIFIER, 1);
            if(null!=identifier)
            {
                this.emitKind(EcmaParserConstants.DOT);
            }
        }

        Node typeArguments=node.findNextDownById(EcmaParserTreeConstants.JJTTYPEARGUMENTS,1);
        if(null!=typeArguments) {
            (new TypeArgumants(this)).generate(typeArguments);
        }
    }
}
