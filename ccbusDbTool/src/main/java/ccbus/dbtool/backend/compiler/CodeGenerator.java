package ccbus.dbtool.backend.compiler;

import ccbus.dbtool.backend.compiler.generator.ServerTerminal;
import ccbus.dbtool.intermediate.icodeimpl.ICodeNodeField;
import ccbus.dbtool.intermediate.routineimpl.NodeCode;


import java.io.OutputStream;
import java.io.PrintWriter;
import ccbus.dbtool.backend.compiler.util.PrintStream;
import ccbus.dbtool.util.Tool;

public class CodeGenerator
{
    protected static PrintStream printStream;
    protected Tool tool;
    public void generate(NodeCode nodeCode) throws Exception
    {

    }

    public CodeGenerator()
    {
    }

    public CodeGenerator(CodeGenerator parent)
    {
        tool=parent.getTool();
    }

    public CodeGenerator(PrintStream ps,Tool t)
    {
        printStream=ps;
        tool=t;
    }


    public void setOut(OutputStream out)
    {
        this.printStream = new PrintStream(out);
    }
    public PrintStream getOut(){return printStream;}

    public void emitNln(String line)
    {
        emit(line);
    }

    public void emitImport(String packageName)
    {
        emit(ServerTerminal.IMPORT+" "+packageName+ServerTerminal.SEMICOLON);

    }

    public void emitPackage(String packageName)
    {
        emit(ServerTerminal.PACKAGE+" "+packageName+ServerTerminal.SEMICOLON);
    }

    public void emitField(ICodeNodeField field)
    {

    }

    public void emitTerminal(ServerTerminal terminal)
    {
        emit(terminal.toString());
    }

    public void emitNewLine()
    {
        emitLn();
    }

    public String valueStringConstant(String str)
    {
        return "\""+str+"\"";
    }

    public void emit(String code)
    {
        printStream.emit(code);
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

    public Tool getTool() {
        return tool;
    }
}
