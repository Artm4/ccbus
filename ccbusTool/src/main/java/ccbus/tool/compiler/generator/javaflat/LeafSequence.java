package ccbus.tool.compiler.generator.javaflat;

import ccbus.tool.intermediate.Node;


public class LeafSequence extends CodeGenerator
{
    protected String separator="";

    public LeafSequence(CodeGenerator codeGenerator)
    {
        super(codeGenerator);
    }

    @Override
    public void generate(Node node)
    {
        for (int i = 0; i < node.jjtGetNumChildren(); ++i)
        {
            if(i>0)
            {
                if (0 == separator.length())
                {
                    emitSpace();
                }
                else
                {
                    emit(separator);
                }
            }
            Node n=node.jjtGetChild(i);
            emit(n.findLeaf().image());
        }
    }

    protected void setSeparator(String sp)
    {
        separator=sp;
    }
}
