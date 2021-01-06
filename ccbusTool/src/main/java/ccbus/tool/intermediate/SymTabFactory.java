package ccbus.tool.intermediate;

import ccbus.tool.intermediate.symtabimpl.*;
import ccbus.tool.parser.java.JavaParserTreeConstants;

public class SymTabFactory
{
    public static SymTabStack createSymTabStack()
    {
        return new SymTabStackImpl();
    }

    public static SymTab createSymTab(int nestingLevel)
    {
        return new SymTabImpl(nestingLevel);
    }

    public static SymTab createSymTab(int nestingLevel,String classNamespace)
    {
        return new SymTabImpl(nestingLevel,classNamespace);
    }

    public static SymTabEntry createSymTabEntry(String name,SymTab symTab)
    {
        return new SymTabEntryImpl(name, symTab);
    }

    public static SymTabEntry createSymTabEntry(Node node,SymTab symTab) throws Exception
    {
        String name;
        if(node.getId()==JavaParserTreeConstants.JJTNAME)
        {
            Node identifier =
                    node.findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER, 1);
            name=identifier.image();
        }
        else
        if(node.getId()==JavaParserTreeConstants.JJTIDENTIFIER)
        {
            name=node.image();
        }
        else
        {
            throw new Exception("Node Identifier or Name required");
        }

        return new SymTabEntryImpl(name, symTab);
    }
}
