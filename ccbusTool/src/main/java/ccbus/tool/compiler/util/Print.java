package ccbus.tool.compiler.util;

import ccbus.tool.intermediate.Node;

public interface Print
{
    public void emit(String code);

    public void emit(Node node);

    public void emitSpace();

    public void emitLn();

    public void indentAdd();
    public void indentRemove();
}
