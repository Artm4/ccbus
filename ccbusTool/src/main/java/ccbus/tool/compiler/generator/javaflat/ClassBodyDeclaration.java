package ccbus.tool.compiler.generator.javaflat;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.java.*;


public class ClassBodyDeclaration extends CodeGenerator
{
    public ClassBodyDeclaration(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node)
    {
        for(int i=0;i<node.jjtGetNumChildren();i++)
        {
            recursiveGenerate(node.jjtGetChild(i));
            if(node.jjtGetChild(i).getId()==JavaParserTreeConstants.JJTMETHODDECLARATION)
            {
                (new MethodDeclaration(this)).generate(node.jjtGetChild(i));
            }
            else
            if(node.jjtGetChild(i).getId()==JavaParserTreeConstants.JJTFIELDDECLARATION)
            {
                emitLn();
                this.emitKind(JavaParserConstants.PUBLIC);
                this.emitSpace();
                this.emitTokenList(node.jjtGetChild(i));
            }
        }
    }

}
