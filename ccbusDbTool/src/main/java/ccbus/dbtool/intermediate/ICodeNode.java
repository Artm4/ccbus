package ccbus.dbtool.intermediate;

import java.util.ArrayList;

public interface ICodeNode
{
    public ICodeNodeType getType();
    public ICodeNode getParent();
    public ICodeNode addChild(ICodeNode node);
    public ArrayList<ICodeNode> getChildren();
    public void setAttribute(ICodeKey key, Object value);
    public Object getAttribute(ICodeKey key);
    public ICodeNode copy();

}
