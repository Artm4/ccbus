package ccbus.system.query.predicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;

public class In extends PredicateBase implements IPredicate
{
    public In(CriteriaBuilder queryBuilder)
    {
        super(queryBuilder);
    }

    @Override
    public <T extends Comparable> CriteriaBuilder.In<T> compare(Expression<T> e, ParameterExpression<T> pe)
    {
        return queryBuilder.in(e);
    }

    @Override
    public <T extends Comparable> CriteriaBuilder.In<T> compare(Expression<T> e, Expression<T> eb)
    {
        return queryBuilder.in(e);
    }
}