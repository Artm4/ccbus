package ccbus.tool.compiler.generator.typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.AngularParserConstants;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;

public class Type extends CodeGenerator {
    public Type(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node)
    {
        Node nodePrimitive=node.findNextDownById(AngularParserTreeConstants.JJTPRIMITIVETYPE,1);
        if(null!=nodePrimitive) {
            emit(nodePrimitive);
        }

        Node nodeReferenceType=node.findNextDownById(AngularParserTreeConstants.JJTREFERENCETYPE,1);
        if(null!=nodeReferenceType)
        {
            (new ReferenceType(this)).generate(nodeReferenceType);
        }
        for(int i=0;i<5;i++)
        {
            Node nodeBracket=node.findNextDownById(AngularParserTreeConstants.JJTBRACKET,1);
            if(null==nodeBracket){break;}
            this.emitKind(AngularParserConstants.LBRACKET);
            this.emitKind(AngularParserConstants.RBRACKET);
        }
    }
}
