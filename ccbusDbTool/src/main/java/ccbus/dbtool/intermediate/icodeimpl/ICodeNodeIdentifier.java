package ccbus.dbtool.intermediate.icodeimpl;

import ccbus.dbtool.intermediate.ICodeNodeType;

public class ICodeNodeIdentifier extends ICodeNodeImpl{
    public ICodeNodeIdentifier(ICodeNodeType type)
    {
        super(type);
    }

    public void setName(String name)
    {
        this.setAttribute(ICodeKeyImpl.NAME,name);
    }

    public String getName()
    {
        return (String)this.getAttribute(ICodeKeyImpl.NAME);
    }
}
