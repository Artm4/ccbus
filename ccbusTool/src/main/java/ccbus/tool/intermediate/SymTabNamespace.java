package ccbus.tool.intermediate;

public interface SymTabNamespace {
    public SymTab lookup(String name);
    public SymTab push(int nestingLevel,String classNamespace);
}
