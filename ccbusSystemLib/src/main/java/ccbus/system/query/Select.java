package ccbus.system.query;

import ccbus.system.test.Point;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.function.Supplier;

public class Select<T> {
    static EntityManager entityManager;
    CriteriaQuery<T> query;
    TypedQuery<T> typedQuery;
    CriteriaBuilder queryBuilder;
    Class<T> classT;
    Root<T> root;
    Map<ParameterExpression,Object> paramList = new HashMap();

    Stack<Predicate> wherePredicates=new Stack<Predicate>();

    private int suffix=0;

    boolean distinct;

    public static EntityManager getEntityManager() {
        return entityManager;
    }

    public static void setEntityManager(EntityManager entityManager) {
        Select.entityManager = entityManager;

    }

    public static <V> Select<V> create(Class<V> cT)
    {
        return new Select<V>(cT);
    }

    public Select(Class<T> cT) {
        classT=cT;
        queryBuilder = entityManager.getCriteriaBuilder();
        query = queryBuilder.createQuery(classT);
        root=query.from(classT);


    }

    public CriteriaQuery<T> getQuery() {
        return query;
    }

    public CriteriaBuilder getQueryBuilder() {
        return queryBuilder;
    }

    public Root<T> getRoot() {
        return root;
    }

    public void distinct(boolean d)
    {
        distinct=d;
    }

    public void join(String joinAttribute)
    {
        root.join(joinAttribute, JoinType.INNER);
    }

    public void joinLeft(String joinAttribute)
    {
        root.join(joinAttribute, JoinType.LEFT);
    }

//whereOr().whereAnd().whereGt().whereLt().whereAnd().whereLt()
//    (gt and lt) or (lt)
//whereAnd().whereOr().whereGt().whereLt().whereOr().whereGt().whereLt()
//    (gt or lt) and (gt lt



    public Select whereOr()
    {
        Predicate or=queryBuilder.or();
        if(!wherePredicates.empty())
        {
            wherePredicates.peek().getExpressions().add(or);
        }
        wherePredicates.push(or);
        return this;
    }

    public Select whereAnd()
    {
        Predicate and=queryBuilder.and();
        if(!wherePredicates.empty())
        {
            wherePredicates.peek().getExpressions().add(and);
        }
        wherePredicates.push(and);
        return this;
    }

    public Select whereClose()
    {
        if(!wherePredicates.empty())
        {
            wherePredicates.pop();
        }
        return this;
    }

    public <V> V createType(Supplier<V> supplier)
    {
        return supplier.get();
    }

    private String nextSuffix()
    {
        return String.valueOf(suffix++);
    }

    public <V extends Comparable> Select whereLt(__MetaField field,V value)
    {
        ParameterExpression pp = field.createParameterExpression(queryBuilder,nextSuffix());
        Expression x = field.createExpression(root);
        Predicate pr= queryBuilder.lessThan(x,pp);
        paramList.put(pp,value);

        wherePredicates.peek().getExpressions().add(pr);
        return this;
    }

    public <V extends Comparable> Select whereGt(__MetaField field,V value)
    {
        ParameterExpression pp = field.createParameterExpression(queryBuilder,nextSuffix());
        Expression x = field.createExpression(root);
        Predicate pr= queryBuilder.greaterThan(x,pp);
        paramList.put(pp,value);

        wherePredicates.peek().getExpressions().add(pr);
        return this;
    }

    public <V extends Comparable> Select whereEq(__MetaField field,V value)
    {
        ParameterExpression pp = field.createParameterExpression(queryBuilder,nextSuffix());

        Expression x = field.createExpression(root);
        Predicate pr= queryBuilder.equal(x,pp);
        paramList.put(pp,value);

        wherePredicates.peek().getExpressions().add(pr);
        return this;
    }

    public List<Object[]> resultArrayList(__MetaField... field)
    {
        return null;
    }

    public List<Tuple> resultTupleList()
    {
        return null;
    }

    public <T> List<T> resultConstructList()
    {
        return null;
    }

    public List<T> resultEntityList()
    {
        return entityManager.createQuery(query).getResultList();
    }

    public void prepareQuery()
    {
        query.where(wherePredicates.get(0));
        typedQuery = entityManager.createQuery(query);

        for(Map.Entry entry:paramList.entrySet())
        {

            ParameterExpression parameterExpression=(ParameterExpression)entry.getKey();
            typedQuery.setParameter((ParameterExpression)entry.getKey(),
                    parameterExpression.getJavaType().cast(entry.getValue()));
        }
    }

    public T resultEntitySingle()
    {
        prepareQuery();
       return typedQuery.getResultList().get(0);
    }

}
