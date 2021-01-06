package ccbus.dbtool.intermediate.icodeimpl;
import ccbus.dbtool.intermediate.ICodeNodeType;

import static ccbus.dbtool.intermediate.icodeimpl.ICodeKeyImpl.NAME;

public class ICodeNodeNotation  extends  ICodeNodeImpl{

    public ICodeNodeNotation(ICodeNodeType type)
    {
        super(type);
    }

    public void setNotationName(String name)
    {
        this.setAttribute(NAME,name);
    }

    public String getNotationName(){return (String)this.getAttribute(NAME);}

    public void addNotationProperty(ICodeNodeKeyValue node)
    {
        this.addChild(node);
    }
}
