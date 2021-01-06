package ccbus.tool.intermediate;

import java.util.ArrayList;

public interface SymTabEntry
{
    public String getName();
    public SymTab getSymTab();
    public void appendLineNumber(int lineNumber);
    public ArrayList<Integer> getLineNumbers();
    /**
     * Set an attribute of the entry.
     * @param key the attribute key.
     * @param value the attribute value.
     */
    public void setAttribute(SymTabKey key, Object value);
    public Object getAttribute(SymTabKey key);
    public boolean hasAttribute(SymTabKey key);
}
