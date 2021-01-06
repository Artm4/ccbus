package ccbus.tool.compiler.generator.ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.EcmaParserConstants;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;

public class AllocationExpression extends CodeGenerator {
    public AllocationExpression(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        Node primitiveType=node.findFirstDownById(EcmaParserTreeConstants.JJTPRIMITIVETYPE,1);
        // It is array allocation of primitives
        if(null!=primitiveType)
        {
            Node arrayInit=node.findFirstDownById(EcmaParserTreeConstants.JJTARRAYINITIALIZER,2);
            // there is already array init
            if(null!=arrayInit)
            {
                this.recursiveGenerate(arrayInit);
            }
            else
            {
                emitKind(EcmaParserConstants.LBRACKET);
                emitKind(EcmaParserConstants.RBRACKET);
            }
        }
        else
        {
            for (int i = 0; i < node.jjtGetNumChildren(); i++)
            {
                this.recursiveGenerate(node.jjtGetChild(i));
                if (EcmaParserTreeConstants.JJTNEWTOKEN == node.jjtGetChild(i).getId())
                {
                    this.emitSpace();
                }
            }
        }
    }
}
