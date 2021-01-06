package ccbus.dbtool.intermediate.icodeimpl;

import ccbus.dbtool.intermediate.ICodeNodeType;

public class ICodeNodeName extends ICodeNodeImpl
{
    private StringBuilder fullNameBuilder=new StringBuilder();

    public ICodeNodeName(ICodeNodeType type)
    {
        super(type);
        this.setAttribute(ICodeKeyImpl.SEPARATOR,".");
    }

    public String getSeparator()
    {
        return (String)this.getAttribute(ICodeKeyImpl.SEPARATOR);
    }

    public void addIdentifier(ICodeNodeIdentifier node)
    {
        if(0==fullNameBuilder.length())
        {
            fullNameBuilder.append(node.getName());
        }
        else
        {
            fullNameBuilder.append(getSeparator()).append(node.getName());
        }
        String fullName = fullNameBuilder.toString();
        this.setAttribute(ICodeKeyImpl.NAME,fullName);

        addChild(node);
        ICodeNodeIdentifier identifier=
                (ICodeNodeIdentifier) getChildren().get(getChildren().size()-1);
        String lastName = (String) identifier.getName();
        this.setAttribute(ICodeKeyImpl.NAME_LAST,lastName);
    }

    public String getFullName()
    {
        return (String)this.getAttribute(ICodeKeyImpl.NAME);
    }

    public String getLastName()
    {
        return (String) this.getAttribute(ICodeKeyImpl.NAME_LAST);
    }

    public void setFullName(String name)
    {
        this.setAttribute(ICodeKeyImpl.NAME,name);
    }
}
