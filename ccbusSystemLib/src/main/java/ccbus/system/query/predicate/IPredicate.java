package ccbus.system.query.predicate;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;

public interface IPredicate
{
    public <T extends Comparable> Predicate compare(Expression<T> e,ParameterExpression<T> pe);
    public <T extends Comparable> Predicate compare(Expression<T> e,Expression<T> eb);
}
