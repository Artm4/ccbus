package ccbus.system.query;

import ccbus.system.query.select.SelectStack;

public class SelectStackEntity<T> extends SelectStack<T,T>
{
    public SelectStackEntity(Class<T> eT)
    {
        super(eT,eT);
    }

    public SelectStack select(__MetaField... cols)
    {
        return this;
    }

}
