package ccbus.dbtool.intermediate.icodeimpl;

import ccbus.dbtool.intermediate.ICodeNode;
import ccbus.dbtool.intermediate.ICodeNodeType;

public class ICodeNodeNotationList extends  ICodeNodeImpl
{
    public ICodeNodeNotationList(ICodeNodeType type)
    {
        super(type);
    }

    public void addNotation(ICodeNodeNotation node)
    {
        this.addChild(node);
    }

    public boolean containsNotationName(String name)
    {
        for(ICodeNode node: this.getChildren())
        {
            ICodeNodeNotation notation=(ICodeNodeNotation) node;
            if(name.equals(notation.getNotationName()))
            {
                return true;
            }
        }
        return false;
    }
}
