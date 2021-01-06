package ccbus.tool.intermediate.symtabimpl;

import java.util.ArrayList;
import java.util.HashMap;
import ccbus.tool.intermediate.*;

@SuppressWarnings("serial")
public class SymTabEntryImpl
    extends HashMap<SymTabKey, Object>
    implements SymTabEntry
{
    private String name;
    private SymTab symTab;
    private ArrayList<Integer> lineNumbers;


    public SymTabEntryImpl(String name, SymTab symTab)
    {
        this.name=name;
        this.symTab=symTab;
        this.lineNumbers=new ArrayList<Integer>();
    }

    public String getName()
    {
        return name;
    }

    public SymTab getSymTab()
    {
        return symTab;
    }

    public void appendLineNumber(int lineNumber)
    {
        this.lineNumbers.add(lineNumber);
    }

    public ArrayList<Integer> getLineNumbers()
    {
        return lineNumbers;
    }

    public void setAttribute(SymTabKey key, Object value)
    {
        put(key,value);
    }

    public Object getAttribute(SymTabKey key)
    {
        return get(key);
    }

    public boolean hasAttribute(SymTabKey key){return containsKey(key);}
}

