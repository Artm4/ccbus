package ccbus.dbtool.intermediate.icodeimpl;

import ccbus.dbtool.intermediate.ICodeFactory;
import ccbus.dbtool.intermediate.ICodeNodeType;

public class ICodeNodeVarDeclarator extends ICodeNodeImpl{
    public ICodeNodeVarDeclarator(ICodeNodeType type) {
        super(type);
        this.children.add(ICodeFactory.createICodeNodeVarId());
        this.children.add(ICodeFactory.createICodeNodeStatement());
    }

    public ICodeNodeIdentifier getNodeIdentifier()
    {
        return (ICodeNodeIdentifier)this.children.get(0);
    }

    public ICodeNodeStatement getNodeInitStatement()
    {
        return (ICodeNodeStatement)this.children.get(1);
    }

    public void setNodeIdentifier(ICodeNodeVarId nodeId)
    {
        this.children.set(0,nodeId);
    }

    public void setNodeInitStatement(ICodeNodeStatement nodeStatement)
    {
        this.children.set(1,nodeStatement);
    }
}
