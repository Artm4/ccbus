package ccbus.dbtool.intermediate.icodeimpl;

import ccbus.dbtool.intermediate.ICodeFactory;

import static ccbus.dbtool.intermediate.icodeimpl.ICodeKeyImpl.*;

public class ICodeNodeField extends ICodeNodeImpl{
    public ICodeNodeField(ccbus.dbtool.intermediate.ICodeNodeType type)
    {
        super(type);
        this.children.add(ICodeFactory.createICodeNodeNotationList());
        this.children.add(ICodeFactory.createICodeNodeTypeId());
        this.children.add(ICodeFactory.createICodeNodeVarDeclarator());
    }

    public void setFieldName(String name)
    {
        this.setAttribute(NAME,name);
    }

    public String getFieldName()
    {
        return (String)this.getAttribute(NAME);
    }

    public ICodeNodeNotationList getNotationList()
    {
        return (ICodeNodeNotationList)this.children.get(0);
    }

    public void setNotationList(ICodeNodeNotationList notationList)
    {
        if(null!=notationList)
        {
            this.children.set(0, notationList);
        }
    }

    public ICodeNodeTypeId getFieldType()
    {
        return (ICodeNodeTypeId)this.children.get(1);
    }

    public void setFieldType(ICodeNodeTypeId fieldType){this.children.set(1,fieldType);}

    public ICodeNodeVarDeclarator getNodeVarDeclarator()
    {
        return (ICodeNodeVarDeclarator)this.children.get(2);
    }

    public void setNodeVarDeclarator(ICodeNodeVarDeclarator nodeVarDeclarator)
    {
        this.children.set(2,nodeVarDeclarator);
    }
}
