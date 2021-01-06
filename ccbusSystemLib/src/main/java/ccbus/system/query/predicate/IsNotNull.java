package ccbus.system.query.predicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;

public class IsNotNull extends PredicateBase implements IPredicate
{
    public IsNotNull(CriteriaBuilder queryBuilder)
    {
        super(queryBuilder);
    }

    @Override
    public <T extends Comparable> Predicate compare(Expression<T> e, ParameterExpression<T> pe)
    {
        return queryBuilder.isNotNull(e);
    }

    @Override
    public <T extends Comparable> Predicate compare(Expression<T> e, Expression<T> eb)
    {
        return queryBuilder.isNotNull(e);
    }
}