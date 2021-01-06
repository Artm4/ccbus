package ccbus.system.query;

import ccbus.system.query.select.SelectStack;

import javax.persistence.criteria.Expression;

public class SelectStackArray<T> extends SelectStack<T,Object[]>
{
    public SelectStackArray(Class<T> eT)
    {
        super(eT,Object[].class);
    }

    protected void prepareExtQuery()
    {
        query.select(queryBuilder.array(selections.toArray(new Expression[]{})));
        super.prepareExtQuery();
    }
}