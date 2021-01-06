package ccbus.tool.compiler.generator.javaflat;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.java.JavaParserTreeConstants;

public class Block extends CodeGenerator {
    public Block(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node) {
        for(int i=0;i<node.jjtGetNumChildren();i++)
        {
            recursiveGenerate(node.jjtGetChild(i));
            if(node.jjtGetChild(i).getId()==JavaParserTreeConstants.JJTALLOCATIONEXPRESSION)
            {

            }
            else
            {
                this.emitTokenList(node.jjtGetChild(i));
            }
        }
    }
}