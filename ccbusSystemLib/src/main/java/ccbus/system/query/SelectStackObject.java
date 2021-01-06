package ccbus.system.query;

import ccbus.system.query.select.SelectStack;

import javax.persistence.criteria.Expression;

public class SelectStackObject<T> extends SelectStack<T,Object>
{
    public SelectStackObject(Class<T> eT)
    {
        super(eT,Object.class);
    }

    protected void prepareExtQuery()
    {
        query.select(queryBuilder.array(selections.toArray(new Expression[]{})));
        super.prepareExtQuery();
    }
}