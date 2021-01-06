package ccbus.dbtool.intermediate.icodeimpl;

import ccbus.dbtool.intermediate.ICodeFactory;
import ccbus.dbtool.intermediate.ICodeNode;
import ccbus.dbtool.intermediate.ICodeNodeType;

import static ccbus.dbtool.intermediate.icodeimpl.ICodeKeyImpl.NAME;
import static ccbus.dbtool.intermediate.icodeimpl.ICodeKeyImpl.QUERY_NAME;

public class ICodeNodeQuery extends ICodeNodeImpl
{
    public ICodeNodeQuery(ICodeNodeType type)
    {
        super(type);
        this.children.add(ICodeFactory.createICodeNodeTable());
        this.children.add(
                ICodeFactory.createICodeNodeList(ICodeNodeTypeImpl.TABLE_LIST)
        );
    }

    public void setQueryName(String name)
    {
        this.setAttribute(QUERY_NAME,name);
    }
    public String getQueryName()
    {
        return (String) this.getAttribute(QUERY_NAME);
    }

    public void setFrom(ICodeNodeTable from)
    {
        this.children.set(0,from);
    }

    public ICodeNodeTable getFrom()
    {
        return (ICodeNodeTable) this.children.get(0);
    }

    public ICodeNodeList getJoinList()
    {
        return (ICodeNodeList)this.children.get(1);
    }

    public void addJoin(ICodeNodeTable join)
    {
        this.children.get(1).addChild(join);
    }

    public boolean hasJoin()
    {
        return this.children.get(1).getChildren().size()>1;
    }
}
