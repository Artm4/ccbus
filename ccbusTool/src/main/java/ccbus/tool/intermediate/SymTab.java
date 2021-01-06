package ccbus.tool.intermediate;

import java.util.ArrayList;

public interface SymTab
{
    public int getNestingLevel();
    public SymTabEntry enter(String name);
    public SymTabEntry lookup(String name);
    boolean isClass();
    void setClassNamespace(String classNamespace);
    String getClassNamespace();
    /**
     * @return a list of symbol table entries sorted by name
     */
    public ArrayList<SymTabEntry> sortedEntries();
}
