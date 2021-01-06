package ccbus.tool.compiler.generator;
import ccbus.tool.compiler.util.PrintStream;
import ccbus.tool.intermediate.Node;

public class CodeGenerator
{
    protected static PrintStream printStream;

    public CodeGenerator()
    {
    }

    public CodeGenerator(CodeGenerator parent)
    {

    }

    public CodeGenerator(PrintStream ps)
    {
        printStream=ps;
    }

    public void generate(Node node)
    {
        process(node);
    }

    private void process(Node node)
    {

    }

    public void emit(String code)
    {
        printStream.emit(code);
    }

    public void emit(Node node)
    {
        printStream.emit(node);
    }

    public void emitSpace()
    {
        printStream.emitSpace();
    }

    public void emitLn()
    {
        printStream.emitLn();
    }

    public void emitKind(int kind)
    {
        emit(kindToString(kind));
    }

    public String kindToString(int kind)
    {
        return "Error";
    }

    public void indentAdd()
    {
        printStream.indentAdd();
    }

    public void indentRemove()
    {
        printStream.indentRemove();
    }


    protected boolean customGenerate(Node node)
    {
        return true;
    }

    protected void recursiveGenerate(Node node)
    {
        if(!customGenerate(node)){return;}

        for(int i=0;i<node.jjtGetNumChildren();i++)
        {
            recursiveGenerate(node.jjtGetChild(i));
        }
        if(0==node.jjtGetNumChildren() && node.jjtGetFirstToken()!=null) {
            this.emit(node);
        }
    }
}
