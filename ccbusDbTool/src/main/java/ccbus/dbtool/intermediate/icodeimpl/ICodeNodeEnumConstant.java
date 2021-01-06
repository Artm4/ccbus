package ccbus.dbtool.intermediate.icodeimpl;

import ccbus.dbtool.intermediate.ICodeFactory;
import ccbus.dbtool.intermediate.ICodeNodeType;
import static ccbus.dbtool.intermediate.icodeimpl.ICodeKeyImpl.*;

public class ICodeNodeEnumConstant extends ICodeNodeImpl
{
    public ICodeNodeEnumConstant(ICodeNodeType type)
    {
        super(type);
        this.children.add(ICodeFactory.createICodeNodeNotationList());
    }

    public void setFieldName(String name)
    {
        this.setAttribute(NAME,name);
    }

    public String getFieldName()
    {
        return (String)this.getAttribute(NAME);
    }

    public ICodeNodeNotationList getNotationList()
    {
        return (ICodeNodeNotationList)this.children.get(0);
    }

    public void setNotationList(ICodeNodeNotationList notationList){this.children.set(0,notationList);}

}
