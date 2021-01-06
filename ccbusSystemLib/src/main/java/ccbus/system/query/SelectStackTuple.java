package ccbus.system.query;

import ccbus.system.query.select.SelectStack;

import javax.persistence.Tuple;
import javax.persistence.criteria.Expression;

public class SelectStackTuple<T> extends SelectStack<T,Tuple>
{
    public SelectStackTuple(Class<T> eT)
    {
        super(eT,Tuple.class);
    }

    protected void prepareExtQuery()
    {
        query.select(queryBuilder.tuple(selections.toArray(new Expression[]{})));
        super.prepareExtQuery();
    }
}