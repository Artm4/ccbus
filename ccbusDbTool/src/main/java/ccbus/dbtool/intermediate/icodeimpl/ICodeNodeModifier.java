package ccbus.dbtool.intermediate.icodeimpl;

import ccbus.dbtool.intermediate.ICodeFactory;
import ccbus.dbtool.intermediate.ICodeNodeType;
import ccbus.dbtool.intermediate.TypeModifierImpl;

public class ICodeNodeModifier extends  ICodeNodeImpl
{
    public ICodeNodeModifier(ICodeNodeType type)
    {
        super(type);
    }

    public void setName(TypeModifierImpl modifier)
    {
        this.setAttribute(ICodeKeyImpl.MODIFIER,modifier);
    }

    public TypeModifierImpl getName()
    {
        return (TypeModifierImpl)this.getAttribute(ICodeKeyImpl.MODIFIER);
    }
}