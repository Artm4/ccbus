package ccbus.tool.compiler.generator.javaflat;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.java.JavaParserConstants;
import ccbus.tool.parser.java.JavaParserTreeConstants;

public class ClassOrInterfaceType extends CodeGenerator
{
    public ClassOrInterfaceType(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        Node identifier = node.findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER, 1);
        while(null != identifier)
        {
            this.emit(identifier);
            identifier = node.findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER, 1);
            if(null!=identifier)
            {
                this.emitKind(JavaParserConstants.DOT);
            }
        }

        Node typeArguments=node.findNextDownById(JavaParserTreeConstants.JJTTYPEARGUMENTS,1);
        if(null!=typeArguments) {
            (new TypeArgumants(this)).generate(typeArguments);
        }
    }
}
