package ccbus.system.query.predicate;

import javax.persistence.criteria.CriteriaBuilder;

public abstract class PredicateBase
{
    CriteriaBuilder queryBuilder;

    public PredicateBase(CriteriaBuilder queryBuilder)
    {
        this.queryBuilder = queryBuilder;
    }
}
