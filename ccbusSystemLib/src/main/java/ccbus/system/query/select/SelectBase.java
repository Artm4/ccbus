package ccbus.system.query.select;


import ccbus.system.query.__MetaClass;
import ccbus.system.query.__MetaField;
import ccbus.system.query.util.CriteriaWhereCallback;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.function.Supplier;

public abstract class SelectBase<E,T>
{
    private EntityManager entityManager;
    static CriteriaWhereCallback criteriaWhereCallback;

    private static final ThreadLocal<EntityManager> entityManagerThreadLocal = new ThreadLocal<EntityManager>();

    protected CriteriaQuery<T> query;
    TypedQuery<T> typedQuery;
    protected CriteriaBuilder queryBuilder;
    protected Class<T> resultT;
    protected Root<E> root;
    Map<ParameterExpression,Object> paramList = new HashMap();

    Deque<SelectExpression> wherePredicates=new ArrayDeque<>();
    Deque<SelectExpression> havingPredicates=new ArrayDeque<>();

    protected ArrayList<Expression> selections=new ArrayList<>();
    protected ArrayList<Expression> groupList=new ArrayList<>();
    protected ArrayList<Order> orderList=new ArrayList<>();

    protected ArrayList<Predicate> wherePredicateList=new ArrayList<>();
    protected ArrayList<Predicate> havingPredicateList=new ArrayList<>();

    protected int suffix=0;
    protected int limit=10000;
    protected int offset=0;

    boolean distinct=false;
    boolean whereCallbackEnabled=true;

    HashMap<String,Join> hashJoin=new HashMap<>();

    public EntityManager getEntityManager() {
        return entityManagerThreadLocal.get();
    }

    public static void setEntityManager(EntityManager entityManager) {
        entityManagerThreadLocal.set(entityManager);
    }

    public static void setCriteriaWhereCallback(CriteriaWhereCallback criteriaWhereCallback) {
        SelectBase.criteriaWhereCallback = criteriaWhereCallback;
    }

    public static CriteriaWhereCallback getCriteriaWhereCallback() {
        return criteriaWhereCallback;
    }

    public SelectBase(Class<E> rootT, Class<T> resT) {
        resultT=resT;
        queryBuilder = getEntityManager().getCriteriaBuilder();
        query = queryBuilder.createQuery(resultT);
        root=query.from(rootT);
    }

    public SelectBase limit(int limit)
    {
        this.limit = limit;
        return this;
    }

    public SelectBase offset(int offset)
    {
        this.offset = offset;
        return this;
    }

    public CriteriaQuery<T> getQuery() {
        return query;
    }

    public CriteriaBuilder getQueryBuilder() {
        return queryBuilder;
    }

    public Root<E> getRoot() {
        return root;
    }

    public SelectBase distinct(boolean d)
    {
        distinct=d;
        return this;
    }

    public Join<Object, Object> join(String joinAttribute)
    {
        return root.join(joinAttribute, JoinType.INNER);
    }


    public <V> V createType(Supplier<V> supplier)
    {
        return supplier.get();
    }

    protected String nextSuffix()
    {
        return String.valueOf(suffix++);
    }

    public void setWhereCallbackEnabled(boolean whereCallbackEnabled) {
        this.whereCallbackEnabled = whereCallbackEnabled;
    }

    protected void prepareSelection(__MetaField... cols)
    {
        for(int i=0;i<cols.length;i++)
        {
            selections.add(createExpression(cols[i]));
        }
    }

    protected void prepareGroup(__MetaField... cols)
    {
        for(int i=0;i<cols.length;i++)
        {
            groupList.add(createExpression(cols[i]));
        }
    }

    protected abstract void prepareExtQuery();

    protected Predicate preparePredicateList(ArrayList<Predicate> predicates)
    {
        if(whereCallbackEnabled && null != criteriaWhereCallback)
        {
            Predicate globalPr=criteriaWhereCallback.onWhere(this.queryBuilder,this.root);
            if(null!=globalPr)
            {
                predicates.add(0, globalPr);
            }
        }
        Predicate first=predicates.get(0);
        Predicate root;
        if(first.getOperator().equals(Predicate.BooleanOperator.AND))
        {
            root=queryBuilder.and();
        }
        else
        {
            root=queryBuilder.or();
        }
        for(Predicate pr:predicates)
        {
            root.getExpressions().add(pr);
        }

        return root;
    }

    public void prepareQuery()
    {
        // should use root predicate. Could be init from overloaded template method
        prepareExtQuery();
        validateQuery();
        if(wherePredicateList.size()>0)
        {
            query.where(preparePredicateList(wherePredicateList));
        }
        if(groupList.size()>0)
        {
            query.groupBy(groupList.toArray(new Expression[]{}));
        }
        if(orderList.size()>0)
        {
            query.orderBy(orderList.toArray(new Order[]{}));
        }
        if(havingPredicateList.size()>0)
        {
            query.having(preparePredicateList(havingPredicateList));
        }
        query.distinct(distinct);

        typedQuery = getEntityManager().createQuery(query);

        typedQuery.setFirstResult(offset);
        typedQuery.setMaxResults(limit);

        for(Map.Entry entry:paramList.entrySet())
        {
            ParameterExpression parameterExpression=(ParameterExpression)entry.getKey();
            typedQuery.setParameter((ParameterExpression)entry.getKey(),
                    parameterExpression.getJavaType().cast(entry.getValue()));
        }
    }

    public void validateQuery()
    {
        if(havingPredicateList.size()>0 && groupList.size()==0)
        {
            throw new SelectException("Group mandatory for having.");
        }
    }

    public List<T> resultList()
    {
        prepareQuery();
        return typedQuery.getResultList();
    }

    public T resultSingle()
    {
        prepareQuery();
        List<T> result = typedQuery.getResultList();
        if(0==result.size())
        {
            return null;
        }
        return result.get(0);
    }

    public SelectBase joinLeft(__MetaClass metaClass)
    {
        if(metaClass.getParent()==null)
        {
            return this;
        }

        Join join=join(metaClass,JoinType.LEFT);

        String hashPath=metaClass.getPathHash();
        hashJoin.put(hashPath,join);

        return this;
    }

    public SelectBase joinRight(__MetaClass metaClass)
    {
        if(metaClass.getParent()==null)
        {
            return this;
        }

        Join join=join(metaClass,JoinType.RIGHT);

        String hashPath=metaClass.getPathHash();
        hashJoin.put(hashPath,join);

        return this;
    }

    //root.getJoins().iterator().next().getAttribute().toString()
    protected <V> Expression<V> createExpression( __MetaField<V> field)
    {
        Path x;

        if(field.getParent().getDepth()!=0)
        {
            Join join;
            join=join(field.getParent());
            x=join.get(field.getName());
        }
        else
        {
            x = root.get(field.getName());
        }

        return x;
    }

    protected Join join(__MetaClass metaClass)
    {
        Join join;
        String hashPath=metaClass.getPathHash();
        if(hashJoin.containsKey(hashPath))
        {
            join=hashJoin.get(hashPath);
        }
        else
        {
            join=join(metaClass,JoinType.INNER);
            hashJoin.put(hashPath,join);
        }
        return join;
    }

    protected Join join(__MetaClass metaClass,JoinType type)
    {
        Join join;

        // parent is root
        if(metaClass.getParent()!=null && metaClass.getParent().getParent()==null)
        {
            join=root.join(metaClass.getSubPath(),type);
        }
        else
        {
            Join jp=join(metaClass.getParent());
            join=jp.join(metaClass.getSubPath(),type);
        }
        return join;
    }
}
