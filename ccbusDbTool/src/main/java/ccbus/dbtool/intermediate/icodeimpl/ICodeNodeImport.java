package ccbus.dbtool.intermediate.icodeimpl;

import ccbus.dbtool.intermediate.ICodeNodeType;

public class ICodeNodeImport extends  ICodeNodeImpl{
    public ICodeNodeImport(ICodeNodeType type)
    {
        super(type);
    }

    public void setName(String name)
    {
        this.setAttribute(ICodeKeyImpl.NAME,name);
    }

    public String getName()
    {
        return (String) this.getAttribute(ICodeKeyImpl.NAME);
    }
}
