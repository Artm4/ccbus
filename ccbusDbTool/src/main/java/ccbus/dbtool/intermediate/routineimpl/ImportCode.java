package ccbus.dbtool.intermediate.routineimpl;

import ccbus.dbtool.intermediate.ICodeNode;
import ccbus.dbtool.intermediate.icodeimpl.ICodeKeyImpl;
import ccbus.dbtool.intermediate.icodeimpl.ICodeNodeImport;

public class ImportCode extends NodeCode
{
    String importDecl;

    public ImportCode(ICodeNodeImport node)
    {
        super(node);
        importDecl=node.getName();
    }

    public String getImportDecl()
    {
        return importDecl;
    }

    public void setImportDecl(String importDecl)
    {
        this.importDecl = importDecl;
    }
}
