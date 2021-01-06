package ccbus.dbtool.intermediate.icodeimpl;

import ccbus.dbtool.intermediate.ICodeFactory;
import ccbus.dbtool.intermediate.ICodeNodeType;
import ccbus.dbtool.intermediate.TypeFormImpl;

import static ccbus.dbtool.intermediate.icodeimpl.ICodeKeyImpl.*;

public class ICodeNodeTypeId extends  ICodeNodeImpl{
    private StringBuilder fullNameBuilder=new StringBuilder();

    public ICodeNodeTypeId(ICodeNodeType type)
    {
        super(type);
        this.children.add(ICodeFactory.createICodeNodeName());
        this.children.add(
            new ITCodeNodeList<ICodeNodeTypeId>(ICodeNodeTypeImpl.TEMPLATE_LIST)
        );
        this.setAttribute(ICodeKeyImpl.SEPARATOR,",");
    }

    public void setForm(TypeFormImpl typeForm)
    {
        this.setAttribute(TYPE,typeForm);
    }

    public TypeFormImpl getForm()
    {
        return (TypeFormImpl)this.getAttribute(TYPE);
    }

    public void setTypeName(String name)
    {
        this.setAttribute(TYPE,name);
    }

    public String getTypeName()
    {
        return (String)this.getAttribute(TYPE);
    }

    public String getSeparator()
    {
        return (String)this.getAttribute(ICodeKeyImpl.SEPARATOR);
    }

    public void addTemplateParam(ICodeNodeTypeId typeDef)
    {
        this.children.get(1).getChildren().add(typeDef);
        //((ITCodeNodeList<ICodeNodeTypeId>)this.children.get(1)).add(typeDef);

        if(0!=fullNameBuilder.length())
        {
            fullNameBuilder.append(getSeparator());
        }

        fullNameBuilder.append(
                typeDef.getTypeName()
        );
        if(typeDef.hasTemplate()) {
            fullNameBuilder.append(
                    typeDef.getTemplateTypeFull()
            );
        }
        String fullName = fullNameBuilder.toString();
        this.setAttribute(TEMPLATE_TYPE,fullName);
    }

    public ITCodeNodeList<ICodeNodeTypeId> getTemplateParams()
    {
        return (ITCodeNodeList<ICodeNodeTypeId>)this.children.get(1);
    }

    public String getTemplateTypeFull()
    {
        return "<"+this.getAttribute(TEMPLATE_TYPE)+">";
    }

    public boolean hasTemplate()
    {
        return this.getTemplateParams().getChildren().size()>0;
    }

    public void setName(ICodeNodeName name)
    {
        this.setTypeName(name.getFullName());
        this.setAttribute(ICodeKeyImpl.TYPE_LAST, name.getLastName());
        this.children.set(0,name);
    }

    public String getTypeNameLast()
    {
        return (String) this.getAttribute(ICodeKeyImpl.TYPE_LAST);
    }

    public ICodeNodeName getName()
    {
        return (ICodeNodeName) this.children.get(0);
    }
}
