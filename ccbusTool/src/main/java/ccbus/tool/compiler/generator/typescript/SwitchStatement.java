package ccbus.tool.compiler.generator.typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;

public class SwitchStatement extends CodeGenerator {

    public SwitchStatement(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        for(int i=0;i<node.jjtGetNumChildren();i++)
        {
            if(decorateNode(node.jjtGetChild(i)))
            {
                recursiveGenerate(node.jjtGetChild(i));
            }
        }
    }

    private boolean decorateNode(Node node)
    {
        switch (node.getId())
        {
            case AngularParserTreeConstants.JJTLBRACETOKEN:
                this.emitLn();
                this.indentAdd();
                return true;
            case AngularParserTreeConstants.JJTRBRACETOKEN:
                this.indentRemove();
                this.emitLn();
                return true;
            case AngularParserTreeConstants.JJTSWITCHLABEL:
                this.emitLn();
                recursiveGenerate(node);
                return false;
            case AngularParserTreeConstants.JJTBLOCKSTATEMENT:
                this.indentAdd();
                recursiveGenerate(node);
                this.indentRemove();
                return false;
        }
        return true;
    }
}
