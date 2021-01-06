package ccbus.tool.compiler.util;
import ccbus.tool.intermediate.Node;

import java.io.OutputStream;
import java.io.PrintWriter;

public class PrintStream implements Print
{
    private PrintWriter printWriter;
    private int intent;
    private int intentSpaces=4;

    public PrintStream(OutputStream out)
    {
        printWriter=new PrintWriter(out);
    }

    public void emit(String code)
    {
        printWriter.print(code);
        printWriter.flush();
    }

    public void emit(Node node)
    {
        printWriter.print(node.image());
        printWriter.flush();
    }

    public void emitSpace()
    {
        printWriter.print(' ');
        printWriter.flush();
    }

    public void emitLn()
    {
        printWriter.println();
        emitIndent();
        printWriter.flush();
    }

    public void indentAdd()
    {
        intent++;
    }

    public void indentRemove()
    {
        intent--;
        if(intent<0)
        {
            intent=0;
        }
    }

    private void emitIndent()
    {
        for(int i=0;i<intentSpaces*intent;i++){printWriter.print(' ');}
    }

}
