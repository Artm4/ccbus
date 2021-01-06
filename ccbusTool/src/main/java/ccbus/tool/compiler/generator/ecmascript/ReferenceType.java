package ccbus.tool.compiler.generator.ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.EcmaParserConstants;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;

public class ReferenceType  extends CodeGenerator {
    public ReferenceType(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node)
    {
        Node nodePrimitive=node.findFirstDownById(EcmaParserTreeConstants.JJTPRIMITIVETYPE,1);
        if(null!=nodePrimitive) {
            emit(nodePrimitive);
        }

        Node classOrInterfaceType=node.findFirstDownById(EcmaParserTreeConstants.JJTCLASSORINTERFACETYPE,1);
        if(null!=classOrInterfaceType)
        {
            (new ClassOrInterfaceType(this)).generate(classOrInterfaceType);
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
