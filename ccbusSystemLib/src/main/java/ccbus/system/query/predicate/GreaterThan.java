package ccbus.system.query.predicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;

public class GreaterThan extends PredicateBase implements IPredicate
{
    public GreaterThan(CriteriaBuilder queryBuilder)
    {
        super(queryBuilder);
    }

    @Override
    public <T extends Comparable> Predicate compare(Expression<T> e,ParameterExpression<T> pe)
    {
        return queryBuilder.greaterThan(e,pe);
    }

    @Override
    public <T extends Comparable> Predicate compare(Expression<T> e, Expression<T> eb)
    {
        return queryBuilder.greaterThan(e,eb);
    }
}
