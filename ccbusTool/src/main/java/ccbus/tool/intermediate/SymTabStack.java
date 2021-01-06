package ccbus.tool.intermediate;

import java.util.HashMap;

public interface SymTabStack
{
    public HashMap getTemplateParamToExtend();
    public int getCurrentNestingLevel();
    public SymTab getLocalSymTab();
    public SymTabEntry enterLocal(String name);
    public SymTabEntry enterClass(String name);
    public SymTabEntry lookupLocal(String name);
    public SymTabEntry lookupSkipTop(String name);
    public SymTabEntry lookup(String name);
    public SymTab lookupSymTab(String name);

    public void addImportHash(String name,String fullName);
    public String lookupImport(String name);


    /**
     * Setter.
     * @param entry the symbol table entry for the main program identifier.
     */
    public void setProgramId(SymTabEntry entry);
    public SymTabEntry getProgramId();
    /**
     * Push a new symbol table onto the stack
     */
    public SymTab push();
    public SymTab push(String name);
    public SymTab push(SymTab symTab);
    public SymTab pop();
    public SymTab pop(Node node);
}
