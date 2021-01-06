package ccbus.dbtool.intermediate.icodeimpl;

import ccbus.dbtool.intermediate.ICodeFactory;

import static ccbus.dbtool.intermediate.icodeimpl.ICodeKeyImpl.*;

public class ICodeNodeClass extends  ICodeNodeImpl{

    public ICodeNodeClass(ccbus.dbtool.intermediate.ICodeNodeType type)
    {
        super(type);
        this.children.add(ICodeFactory.createICodeNodeNotationList());

        this.children.add(ICodeFactory.createICodeNodeTypeId());
        this.children.add(ICodeFactory.createICodeNodeList(ICodeNodeTypeImpl.FIELD_LIST));

        this.children.add(new ITCodeNodeList<ICodeNodeTypeId>(ICodeNodeTypeImpl.EXTEND_LIST));
        this.children.add(new ITCodeNodeList<ICodeNodeTypeId>(ICodeNodeTypeImpl.IMPLEMENT_LIST));

        this.children.add(new ITCodeNodeList<ICodeNodeModifier>(ICodeNodeTypeImpl.MODIFIER_LIST));
        this.children.add(new ITCodeNodeList<ICodeNodeImport>(ICodeNodeTypeImpl.IMPORT_LIST));

    }

    public void setClassType(String classType)
    {
        this.setAttribute(CLASS_TYPE,classType);
    }

    public String getClassType()
    {
        return (String)this.getAttribute(CLASS_TYPE);
    }

    public void setClassName(String className)
    {
        this.getNodeClassType().setTypeName(className);
    }

    public String getClassName()
    {
        return (String)this.getNodeClassType().getTypeName();
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
    public ICodeNodeTypeId getNodeClassType()
    {
        return (ICodeNodeTypeId)this.children.get(1);
    }
    public ICodeNodeList getFieldList()
    {
        return (ICodeNodeList)this.children.get(2);
    }
    public ITCodeNodeList<ICodeNodeTypeId> getExtendsList()
    {
        return (ITCodeNodeList<ICodeNodeTypeId>)this.children.get(3);
    }
    public ITCodeNodeList<ICodeNodeTypeId>  getImplementsList()
    {
        return (ITCodeNodeList<ICodeNodeTypeId>)this.children.get(4);
    }
    public ITCodeNodeList<ICodeNodeModifier> getModifierList()
    {
        return (ITCodeNodeList<ICodeNodeModifier>)this.children.get(5);
    }
    public ITCodeNodeList<ICodeNodeImport> getImportList()
    {
        return (ITCodeNodeList<ICodeNodeImport>)this.children.get(6);
    }
    public void setImportList(ITCodeNodeList<ICodeNodeImport> importList)
    {
        if(null!=importList)
        {
            this.children.set(6, importList);
        }
    }

    public void setBaseName(String name)
    {
        this.setAttribute(BASE_NAME,name);
    }

    public String getBaseName()
    {
        return (String)this.getAttribute(BASE_NAME);
    }

}
