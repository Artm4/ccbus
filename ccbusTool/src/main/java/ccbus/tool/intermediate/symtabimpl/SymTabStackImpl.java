package ccbus.tool.intermediate.symtabimpl;

import ccbus.tool.intermediate.*;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("serial")
public class SymTabStackImpl
    extends ArrayList<SymTab>
    implements SymTabStack
{
    private int currentNestingLevel;
    private SymTabEntry programId;
    public SymTabNamespace symTabNamespace;
    public HashMap<String,String> templateParamToExtend=new HashMap<>();
    public HashMap<String,String> importHash=new HashMap<>();

    public SymTabStackImpl()
    {
        this.currentNestingLevel=0;
        // Global namespace not needed in Java, but PHP/C/Python
        add(SymTabFactory.createSymTab(currentNestingLevel));
        symTabNamespace=new SymTabNamespaceImpl();
    }
    
    public int getCurrentNestingLevel()
    {
        return currentNestingLevel;
    }

    public HashMap getTemplateParamToExtend()
    {
        return this.templateParamToExtend;
    }

    public SymTab getLocalSymTab()
    {
        return get(currentNestingLevel);
    }
    public SymTabEntry enterLocal(String name)
    {
        return get(currentNestingLevel).enter(name);
    }

    public SymTabEntry enterClass(String name)
    {
        SymTabEntry enteredEntry=null;
        for(int i=currentNestingLevel;(i>=0) && (enteredEntry==null);--i)
        {
            SymTab symTab=get(i);
            if(symTab.isClass())
            {
                enteredEntry=symTab.enter(name);
            }
        }
        return enteredEntry;
    }

    public SymTabEntry lookupLocal(String name)
    {
        return get(currentNestingLevel).lookup(name);
    }

    public SymTabEntry lookupSkipTop(String name)
    {
        SymTabEntry foundEntry=null;

        for(int i=currentNestingLevel-1;(i>=0) && (foundEntry==null);--i)
        {
            foundEntry=get(i).lookup(name);
        }

        return foundEntry;
    }

    public SymTabEntry lookup(String name)
    {
        SymTabEntry foundEntry=null;

        for(int i=currentNestingLevel;(i>=0) && (foundEntry==null);--i)
        {
            foundEntry=get(i).lookup(name);
        }
 
        return foundEntry;
    }
    public SymTab lookupSymTab(String name)
    {
        // check if it is template parameter that is extended, use extend type instead for explicit declaration
        if(templateParamToExtend.containsKey(name))
        {
            name=templateParamToExtend.get(name);
        }
        return symTabNamespace.lookup(name);
    }

    public SymTab push()
    {
        SymTab symTab=SymTabFactory.createSymTab(++currentNestingLevel);
        add(symTab);

        return symTab;
    }

    public SymTab push(String name)
    {
        SymTab symTab = symTabNamespace.push(++currentNestingLevel, name);
        add(symTab);
        return symTab;
    }

    public SymTab push(SymTab symTab)
    {   
        ++currentNestingLevel;
        add(symTab);

        return symTab;
    }   

    public SymTab pop()
    {
        SymTab symTab=get(currentNestingLevel);
        remove(currentNestingLevel--);
        
        return symTab;
    }

    public SymTab pop(Node node)
    {
        SymTab symTab=get(currentNestingLevel);
        node.symTab(symTab);
        return pop();
    }

    public void addImportHash(String name,String fullName)
    {
        importHash.put(name,fullName);
    }

    public String lookupImport(String name)
    {
        return importHash.get(name);
    }

    public void setProgramId(SymTabEntry entry)
    {
        this.programId=entry;
    }

    public SymTabEntry getProgramId()
    {
        return this.programId;
    }
}
