package ccbus.tool.compiler.generator.typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.AngularParserConstants;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;

public class TypeArgumants extends CodeGenerator {
    public TypeArgumants(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        this.emitKind(AngularParserConstants.LT);
        for(int i=0;i<5;i++)
        {
            Node nodeReference=node.findNextDownById(AngularParserTreeConstants.JJTREFERENCETYPE,1);
            if(null==nodeReference){break;}
            if(0!=i){this.emitKind(AngularParserConstants.COMMA);}
            (new ReferenceType(this)).generate(nodeReference);
        }
        this.emitKind(AngularParserConstants.GT);
    }

}
