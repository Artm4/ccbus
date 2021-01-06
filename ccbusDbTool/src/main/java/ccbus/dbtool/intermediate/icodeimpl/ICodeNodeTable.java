package ccbus.dbtool.intermediate.icodeimpl;

import ccbus.dbtool.intermediate.ICodeFactory;
import ccbus.dbtool.intermediate.ICodeNode;
import ccbus.dbtool.intermediate.ICodeNodeType;
import static ccbus.dbtool.intermediate.icodeimpl.ICodeKeyImpl.*;

public class ICodeNodeTable extends ICodeNodeImpl
{
    enum JoinType
    {
        INNER,
        LEFT,
    }

    public ICodeNodeTable(ICodeNodeType type)
    {
        super(type);
        this.children.add(ICodeFactory.createICodeNodeFieldList());
    }

    public void setTableName(String name)
    {
        this.setAttribute(NAME,name);
    }

    public void setJoinType(JoinType joinType)
    {
        this.setAttribute(JOIN_TYPE,joinType);
    }

    public JoinType setJoinType()
    {
        return (JoinType)this.getAttribute(JOIN_TYPE);
    }

    public String getTableName()
    {
        return (String)this.getAttribute(NAME);
    }

    public void setFieldList(ICodeNode fieldList)
    {
        this.children.set(0,fieldList);
    }

    public ICodeNode getFieldList()
    {
        return this.children.get(0);
    }
}
