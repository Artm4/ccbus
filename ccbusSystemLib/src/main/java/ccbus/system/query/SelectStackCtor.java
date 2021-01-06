package ccbus.system.query;

import ccbus.system.query.select.SelectStack;

import javax.persistence.criteria.Expression;

public class SelectStackCtor<E,R> extends SelectStack<E,R>
{
    public SelectStackCtor(Class<E> eT, Class<R> rT)
    {
        super(eT,rT);
    }

    protected void prepareExtQuery()
    {
        query.select(queryBuilder.construct(resultT,selections.toArray(new Expression[]{})));
        super.prepareExtQuery();
    }
}