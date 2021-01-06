package ccbus.tool.compiler.generator.ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.EcmaParserConstants;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;

public class TypeArgumants extends CodeGenerator {
    public TypeArgumants(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        this.emitKind(EcmaParserConstants.LT);
        for(int i=0;i<5;i++)
        {
            Node nodeReference=node.findNextDownById(EcmaParserTreeConstants.JJTREFERENCETYPE,1);
            if(null==nodeReference){break;}
            if(0!=i){this.emitKind(EcmaParserConstants.COMMA);}
            (new ReferenceType(this)).generate(nodeReference);
        }
        this.emitKind(EcmaParserConstants.GT);
    }

}
