package ccbus.tool.compiler.generator.typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;

public class ClassOrInterfaceType extends CodeGenerator
{
    public ClassOrInterfaceType(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        Node identifier=node.findFirstDownById(AngularParserTreeConstants.JJTIDENTIFIER,1);
        if(null!=identifier) {
            this.emit(identifier);
        }

        Node typeArguments=node.findNextDownById(AngularParserTreeConstants.JJTTYPEARGUMENTS,1);
        if(null!=typeArguments) {
            (new TypeArgumants(this)).generate(typeArguments);
        }
    }
}
