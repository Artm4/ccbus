package ccbus.dbtool.intermediate.icodeimpl;

import ccbus.dbtool.intermediate.ICodeFactory;
import ccbus.dbtool.intermediate.ICodeNodeType;

public class ICodeNodeVarId  extends ICodeNodeImpl{
    public ICodeNodeVarId(ICodeNodeType type) {
        super(type);
        this.children.add(ICodeFactory.createICodeNodeIdentifier());
    }

    public ICodeNodeIdentifier getNodeIdentifier()
    {
        return (ICodeNodeIdentifier)this.children.get(0);
    }
}
