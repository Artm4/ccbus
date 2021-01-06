package ccbus.system.query.util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface CriteriaWhereCallback
{
    <E> Predicate onWhere(CriteriaBuilder criteriaBuilder,Root<E> root);
}
