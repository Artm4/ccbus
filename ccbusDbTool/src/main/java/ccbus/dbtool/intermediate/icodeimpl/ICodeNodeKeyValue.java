package ccbus.dbtool.intermediate.icodeimpl;

import ccbus.dbtool.intermediate.ICodeNodeType;

import static ccbus.dbtool.intermediate.icodeimpl.ICodeKeyImpl.*;

public class ICodeNodeKeyValue extends  ICodeNodeImpl
{
    public ICodeNodeKeyValue(ICodeNodeType type)
    {
        super(type);
        setKey("");
        setValue("");
    }

    public void setKey(String key)
    {
        this.setAttribute(KEY,key);
    }

    public void setValue(String value)
    {
        this.setAttribute(VALUE_RAW,value);
        this.setAttribute(VALUE,value.replace("\"",""));
    }

    public String getKey()
    {
        return (String)this.getAttribute(KEY);
    }

    public String getValue()
    {
        return (String)this.getAttribute(VALUE);
    }

    public String getValueRaw()
    {
        return (String)this.getAttribute(VALUE_RAW);
    }

}
