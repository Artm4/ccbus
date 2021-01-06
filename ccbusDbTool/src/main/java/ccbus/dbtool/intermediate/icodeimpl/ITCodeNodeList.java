package ccbus.dbtool.intermediate.icodeimpl;

import ccbus.dbtool.intermediate.ICodeNode;
import ccbus.dbtool.intermediate.ICodeNodeType;

import java.util.ArrayList;

public class ITCodeNodeList<T extends ICodeNode> extends  ICodeNodeImpl
{
    protected ArrayList<T> list=new ArrayList<>();
    public ITCodeNodeList(ICodeNodeType type)
    {
        super(type);
    }

    public T add(T node)
    {
        if(node!=null)
        {
            this.list.add(node);
            ((ICodeNodeImpl) node).parent=this;
        }
        return node;
    }

    public ArrayList<T> getList()
    {
        return list;
    }
    public void addList(ITCodeNodeList<T> nodeList)
    {
        this.list.addAll(nodeList.getList());
    }
}
