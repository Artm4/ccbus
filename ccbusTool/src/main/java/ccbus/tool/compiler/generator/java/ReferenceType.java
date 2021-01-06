package ccbus.tool.compiler.generator.java;


import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.java.JavaParserConstants;
import ccbus.tool.parser.java.JavaParserTreeConstants;

public class ReferenceType extends CodeGenerator {
    public ReferenceType(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node)
    {
        Node nodePrimitive=node.findFirstDownById(JavaParserTreeConstants.JJTPRIMITIVETYPE,1);
        if(null!=nodePrimitive) {
            emit(nodePrimitive);
        }

        Node classOrInterfaceType=node.findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACETYPE,1);
        if(null!=classOrInterfaceType)
        {
            (new ClassOrInterfaceType(this)).generate(classOrInterfaceType);
        }

        for(int i=0;i<5;i++)
        {
            Node nodeBracket=node.findNextDownById(JavaParserTreeConstants.JJTBRACKET,1);
            if(null==nodeBracket){break;}
            this.emitKind(JavaParserConstants.LBRACKET);
            this.emitKind(JavaParserConstants.RBRACKET);
        }
    }
}
