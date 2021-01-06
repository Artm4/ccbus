package ccbus.tool.intermediate;

import ccbus.tool.util.java.Misc;
import ccbus.tool.util.Pair;

import java.util.LinkedHashMap;
import java.util.TreeMap;

public class ParsedNamespaceTab  extends LinkedHashMap<String,Node>
{
    public Node lookup(String name)
    {
        return get(name);
    }

    public Node enter(String name,Node compilationUnit)
    {
        put(name,compilationUnit);
        return compilationUnit;
    }

    public Node lookupByClassName(String name)
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
