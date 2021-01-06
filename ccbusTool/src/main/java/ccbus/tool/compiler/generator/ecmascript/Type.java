package ccbus.tool.compiler.generator.ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.EcmaParserConstants;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;

public class Type extends CodeGenerator {
    public Type(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node)
    {
        Node nodePrimitive=node.findNextDownById(EcmaParserTreeConstants.JJTPRIMITIVETYPE,1);
        if(null!=nodePrimitive) {
            emit(nodePrimitive);
        }

        Node nodeReferenceType=node.findNextDownById(EcmaParserTreeConstants.JJTREFERENCETYPE,1);
        if(null!=nodeReferenceType)
        {
            (new ReferenceType(this)).generate(nodeReferenceType);
        }
        for(int i=0;i<5;i++)
        {
            Node nodeBracket=node.findNextDownById(EcmaParserTreeConstants.JJTBRACKET,1);
            if(null==nodeBracket){break;}
            this.emitKind(EcmaParserConstants.LBRACKET);
            this.emitKind(EcmaParserConstants.RBRACKET);
        }
    }
}
