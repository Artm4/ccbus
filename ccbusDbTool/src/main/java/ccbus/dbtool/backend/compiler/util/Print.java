package ccbus.dbtool.backend.compiler.util;


public interface Print
{
    public void emit(String code);

    public void emitSpace();

    public void emitLn();

    public void indentAdd();
    public void indentRemove();
}
