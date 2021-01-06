package ccbus.tool.intermediate.symtabimpl;

import ccbus.tool.intermediate.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

@SuppressWarnings("serial")
public class SymTabImpl
    extends TreeMap<String,SymTabEntry>
    implements SymTab
{
    private int nestingLevel;
    String classNamespace="";

    public SymTabImpl(int nestingLevel)
    {
        this.nestingLevel=nestingLevel;
    }

    public SymTabImpl(int nestingLevel,String classNamespace)
    {
        this.nestingLevel=nestingLevel;
        this.classNamespace=classNamespace;
    }

    public int getNestingLevel()
    {
        return nestingLevel;
    }

    public String getClassNamespace()
    {
        return classNamespace;
    }

    public void setClassNamespace(String classNamespace)
    {
        this.classNamespace = classNamespace;
    }

    public SymTabEntry enter(String name)
    {
        SymTabEntry entry=SymTabFactory.createSymTabEntry(name,this);
        put(name,entry);

        return entry;
    }

    public boolean isClass()
    {
        return classNamespace.length()>0;
    }

    public SymTabEntry lookup(String name)
    {
       return get(name);
    }

    public ArrayList<SymTabEntry> sortedEntries()
    {
        Collection<SymTabEntry> entries=values();
        Iterator<SymTabEntry> iter=entries.iterator();
        ArrayList<SymTabEntry> list=new ArrayList<SymTabEntry>(size());

        while(iter.hasNext())
        {
            list.add(iter.next());
        }

        return list;
    }
}
