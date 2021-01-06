package ccbus.tool.intermediate.symtabimpl;

import ccbus.tool.intermediate.SymTab;
import ccbus.tool.intermediate.SymTabFactory;
import ccbus.tool.intermediate.SymTabNamespace;

import java.util.TreeMap;

public class SymTabNamespaceImpl extends TreeMap<String,SymTab> implements SymTabNamespace
{
    public SymTab push(int nestingLevel,String classNamespace)
    {
        if(containsKey(classNamespace))
        {
            return get(classNamespace);
        }
        SymTab symTab=SymTabFactory.createSymTab(nestingLevel);
        symTab.setClassNamespace(classNamespace);
        put(classNamespace,symTab);
        return symTab;
    }

    public SymTab lookup(String name)
    {
        return get(name);
    }
}

