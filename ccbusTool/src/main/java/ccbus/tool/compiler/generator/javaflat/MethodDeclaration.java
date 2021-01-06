package ccbus.tool.compiler.generator.javaflat;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.java.JavaParserConstants;
import ccbus.tool.parser.java.JavaParserTreeConstants;

public class MethodDeclaration extends CodeGenerator {
    public MethodDeclaration(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node)
    {
        for(int i=0;i<node.jjtGetNumChildren();i++)
        {
            recursiveGenerate(node.jjtGetChild(i));
            if(node.jjtGetChild(i).getId()==JavaParserTreeConstants.JJTBLOCK)
            {
                  (new Block(this)).generate(node.jjtGetChild(i));
            }
            else
            {
                this.emitTokenList(node.jjtGetChild(i));
            }
        }
    }
}