package ccbus.tool.compiler.generator.typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.AngularParserConstants;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;

public class ReferenceType  extends CodeGenerator {
    public ReferenceType(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node)
    {
        Node nodePrimitive=node.findFirstDownById(AngularParserTreeConstants.JJTPRIMITIVETYPE,1);
        if(null!=nodePrimitive) {
            emit(nodePrimitive);
        }

        Node classOrInterfaceType=node.findFirstDownById(AngularParserTreeConstants.JJTCLASSORINTERFACETYPE,1);
        if(null!=classOrInterfaceType)
        {
            (new ClassOrInterfaceType(this)).generate(classOrInterfaceType);
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
