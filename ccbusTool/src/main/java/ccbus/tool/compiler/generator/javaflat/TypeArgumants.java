package ccbus.tool.compiler.generator.javaflat;


import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.java.JavaParserConstants;
import ccbus.tool.parser.java.JavaParserTreeConstants;

public class TypeArgumants extends CodeGenerator {
    public TypeArgumants(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        this.emitKind(JavaParserConstants.LT);
        for(int i=0;i<5;i++)
        {
            Node nodeReference=node.findNextDownById(JavaParserTreeConstants.JJTREFERENCETYPE,2);
            if(null==nodeReference){break;}
            if(0!=i){this.emitKind(JavaParserConstants.COMMA);}
            (new ReferenceType(this)).generate(nodeReference);
        }
        this.emitKind(JavaParserConstants.GT);
    }

}
