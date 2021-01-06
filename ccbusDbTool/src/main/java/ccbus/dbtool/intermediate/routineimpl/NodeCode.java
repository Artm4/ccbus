package ccbus.dbtool.intermediate.routineimpl;

import ccbus.dbtool.intermediate.ICodeNode;

public class NodeCode
{
    protected ICodeNode node;

    public ICodeNode getNode()
    {
        return node;
    }

    public NodeCode(ICodeNode node)
    {
        this.node = node;
    }
}
