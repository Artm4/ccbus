package ccbus.dbtool.intermediate;

import ccbus.dbtool.intermediate.routineimpl.NodeCode;
import ccbus.dbtool.util.Misc;

import java.util.LinkedHashMap;

public class ParsedNamespaceTab extends LinkedHashMap<String,NodeCode>
{
    public NodeCode lookup(String name)
    {
        return get(name);
    }

    public NodeCode enter(String name,NodeCode compilationUnit)
    {
        put(name,compilationUnit);
        return compilationUnit;
    }

    public NodeCode lookupByClassName(String name)
    {
        for(String key : this.keySet())
        {
            String fileName= Misc.fileSeparator+name+".java";
            if(key.endsWith(fileName))
            {
                return lookup(key);
            }
        }
        return null;
    }
}
