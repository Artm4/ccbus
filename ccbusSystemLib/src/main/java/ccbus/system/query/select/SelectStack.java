package ccbus.system.query.select;

import ccbus.system.query.__MetaClass;
import ccbus.system.query.__MetaField;
import ccbus.system.query.predicate.*;

import javax.persistence.criteria.*;

import java.util.*;

import static ccbus.system.query.select.ConditionType.*;
import static ccbus.system.query.select.SelectExpressionType.*;

public abstract class  SelectStack<E,T> extends SelectBase<E,T>
{
    SelectExpression selectExpression=new SelectExpression(WHERE);
    SelectCmp<E,T> selectCmp=new SelectCmp<>(this);
    SelectFunction<E,T> selectFunction=new SelectFunction<>(this);

    public SelectStack(Class<E> eT,Class<T> rT) {
       super(eT,rT);
    }

    protected ConditionType conditionType;

    public SelectExpression getSelectExpression()
    {
        return selectExpression;
    }

    public SelectExpression prepareConditionExpression()
    {
        Deque<SelectExpression> predicateStack;
        if(conditionType.equals(WHERE))
        {
            predicateStack=wherePredicates;
        }
        else
        {
            predicateStack=havingPredicates;
        }
        selectExpression=new SelectExpression(conditionType);
        predicateStack.peekLast().getExpressions().add(selectExpression);
        return selectExpression;
    }

    public SelectStack predicateCond(Deque<SelectExpression> predicateStack,ConditionType type,SelectExpressionType eType)
    {
        SelectExpression cond=new SelectExpression(type,eType);
        if(!predicateStack.isEmpty())
        {
            predicateStack.peek().getExpressions().add(cond);
        }
        predicateStack.add(cond);
        conditionType=type;

        return this;
    }

    public SelectStack whereOr()
    {
        return predicateCond(wherePredicates,WHERE,OR);
    }

    public SelectStack whereAnd()
    {
        return predicateCond(wherePredicates,WHERE,AND);
    }

    public SelectStack whereClose()
    {
        if(!wherePredicates.isEmpty())
        {
            wherePredicates.removeLast();
        }
        return this;
    }

    public SelectStack havingOr()
    {
        return predicateCond(havingPredicates,HAVING,OR);
    }

    public SelectStack havingAnd()
    {
        return predicateCond(havingPredicates,HAVING,AND);
    }


    public SelectStack havingClose()
    {
        if(!havingPredicates.isEmpty())
        {
            havingPredicates.pop();
        }
        return this;
    }

    public SelectStack distinct(boolean d)
    {
        super.distinct(d);
        return this;
    }

    public SelectStack limit(int limit)
    {
        super.limit(limit);
        return this;
    }

    public SelectStack offset(int offset)
    {
        super.offset(offset);
        return this;
    }

    protected Predicate whereCondition(SelectExpression se)
    {
        __MetaField field=se.getField();
        __MetaField fieldSecond=se.getFieldSecond();
        Object value=se.getValue();
        List<Object> valueList=se.getValueList();
        IPredicate predicate=se.getPredicate();

        Expression func=se.getFunc();
        if(func==null && field==null)
        {
            throw new SelectException("Missing field parameter for predicate "+se.toString());
        }
        Expression e = func!=null ? func : this.createExpression(field);

        Predicate pr=null;
        if(null!=value)
        {
            ParameterExpression pp;
            if(null!=func)
            {
                Class funcType = func.getJavaType();
                pp = queryBuilder.parameter(funcType,"func_"+nextSuffix());
            }
            else
            {
                pp = field.createParameterExpression(queryBuilder, nextSuffix());
            }
            paramList.put(pp, value);
            pr= predicate.compare(e,pp);
        }
        else
        if(null!=valueList && predicate instanceof In)
        {
            CriteriaBuilder.In prIn=(CriteriaBuilder.In)predicate.compare(e,null);
            for(Object item: valueList)
            {
                ParameterExpression pp = field.createParameterExpression(queryBuilder, nextSuffix());
                paramList.put(pp, item);
                prIn.value(pp);
            }
            pr=prIn;
        }
        else
        if(null!=fieldSecond)
        {
            Expression xb=createExpression(fieldSecond);
            pr=predicate.compare(e,xb);
        }
        else
        if(predicate instanceof IsNull || predicate instanceof IsNotNull)
        {
            pr=predicate.compare(e,null);
        }
        else
        {
            throw new SelectException("Missing field or value to compare for predicate "+se.toString());
        }

        return pr;
    }

    public <V extends List> SelectStack cmpIn(__MetaField field, V value)
    {
        return selectCmp.cmpIn(field,value);
    }

    public <V extends List> SelectStack cmpIsNull(__MetaField field)
    {
        return selectCmp.cmpIsNull(field);
    }

    public <V extends List> SelectStack cmpIsNotNull(__MetaField field)
    {
        return selectCmp.cmpIsNotNull(field);
    }

    public <V extends Comparable> SelectStack cmpLessThan(__MetaField field, V value)
    {
        return selectCmp.cmpLessThan(field,value);
    }
    public <V extends Comparable> SelectStack cmpLessThan(V value)
    {
        return selectCmp.cmpLessThan(value);
    }
    public SelectStack cmpLessThan(__MetaField field)
    {
        return selectCmp.cmpLessThan(field);
    }

    public <V extends Comparable> SelectStack cmpGreaterThan(__MetaField field, V value)
    {
        return selectCmp.cmpGreaterThan(field,value);
    }
    public <V extends Comparable> SelectStack cmpGreaterThan(V value)
    {
        return selectCmp.cmpGreaterThan(value);
    }
    public SelectStack cmpGreaterThan(__MetaField field)
    {
        return selectCmp.cmpGreaterThan(field);
    }

    public <V extends Comparable> SelectStack cmpEqual(__MetaField field, V value)
    {
        return selectCmp.cmpEqual(field,value);
    }
    public <V extends Comparable> SelectStack cmpEqual(V value)
    {
        return selectCmp.cmpEqual(value);
    }
    public SelectStack cmpEqual(__MetaField field)
    {
        return selectCmp.cmpEqual(field);
    }

    public <V extends Comparable> SelectStack cmpNotEqual(__MetaField field, V value)
    {
        return selectCmp.cmpNotEqual(field,value);
    }
    public <V extends Comparable> SelectStack cmpNotEqual(V value)
    {
        return selectCmp.cmpNotEqual(value);
    }
    public SelectStack cmpNotEqual(__MetaField field)
    {
        return selectCmp.cmpNotEqual(field);
    }

    public <V extends Comparable> SelectStack cmpGreaterThanOrEqual(__MetaField field, V value)
    {
        return selectCmp.cmpGreaterThanOrEqual(field,value);
    }
    public <V extends Comparable> SelectStack cmpGreaterThanOrEqual(V value)
    {
        return selectCmp.cmpGreaterThanOrEqual(value);
    }
    public SelectStack cmpGreaterThanOrEqual(__MetaField field)
    {
        return selectCmp.cmpGreaterThanOrEqual(field);
    }


    public <V extends Comparable> SelectStack cmpLessThanOrEqual(__MetaField field, V value)
    {
        return selectCmp.cmpLessThanOrEqual(field,value);
    }
    public <V extends Comparable> SelectStack cmpLessThanOrEqual(V value)
    {
        return selectCmp.cmpLessThanOrEqual(value);
    }
    public SelectStack cmpLessThanOrEqual(__MetaField field)
    {
        return selectCmp.cmpLessThanOrEqual(field);
    }

    public SelectStack par(__MetaField field)
    {
        selectExpression.setField(field);
        selectExpression.setStateValueForBinaryExpression();
        return this;
    }

    public SelectStack select(__MetaField... cols)
    {
        prepareSelection(cols);
        conditionType=SELECT;
        return this;
    }

    public SelectStack group(__MetaField... cols)
    {
        prepareGroup(cols);
        return this;
    }

    public SelectStack orderAsc(__MetaField field)
    {
        this.orderList.add(queryBuilder.asc(createExpression(field)));
        return this;
    }

    public SelectStack orderDesc(__MetaField field)
    {
        this.orderList.add(queryBuilder.desc(createExpression(field)));
        return this;
    }

    public SelectStack joinLeft(__MetaClass metaClass)
    {
        super.joinLeft(metaClass);
        return this;
    }

    public SelectStack joinRight(__MetaClass metaClass)
    {
        super.joinRight(metaClass);
        return this;
    }


    protected void setFunctionExpression(Expression e)
    {
        if(conditionType==SELECT)
        {
            selections.add(e);
        }
        else
        if(conditionType==HAVING || conditionType==WHERE)
        {
            selectExpression.setFunc(e);
        }
    }

    public SelectStack fCount(__MetaField field)
    {
        selectFunction.fCount(field);
        return this;
    }

    public SelectStack fCountDistinct(__MetaField field)
    {
        selectFunction.fCountDistinct(field);
        return this;
    }

    public SelectStack fSum(__MetaField field)
    {
        selectFunction.fSum(field);
        return this;
    }

    public SelectStack fSumAsLong(__MetaField field)
    {
        selectFunction.fSumAsLong(field);
        return this;
    }

    public SelectStack fSumAsDouble(__MetaField field)
    {
        selectFunction.fSumAsDouble(field);
        return this;
    }

    public SelectStack fAvg(__MetaField field)
    {
        selectFunction.fAvg(field);
        return this;
    }

    public SelectStack fMin(__MetaField field)
    {
        selectFunction.fMin(field);
        return this;
    }

    public SelectStack fLeast(__MetaField field)
    {
        selectFunction.fLeast(field);
        return this;
    }

    public SelectStack fMax(__MetaField field)
    {
        selectFunction.fMax(field);
        return this;
    }

    public SelectStack fGreatest(__MetaField field)
    {
        selectFunction.fGreatest(field);
        return this;
    }

    public SelectStack fLike(__MetaField field, String value)
    {
        selectFunction.fLike(field,value);
        return this;
    }

    public SelectStack fConcat(__MetaField field, String value)
    {
        selectFunction.fConcat(field,value);
        return this;
    }

    public SelectStack fConcat(__MetaField field, __MetaField field2)
    {
        selectFunction.fConcat(field,field2);
        return this;
    }

    public SelectStack fConcat(__MetaField field, String value, __MetaField field2)
    {
        selectFunction.fConcat(field,value,field2);
        return this;
    }

    public SelectStack fSubstring(__MetaField field, int pos)
    {
        selectFunction.fSubstring(field,pos);
        return this;
    }

    public SelectStack fSubstring(__MetaField field, int pos, int length)
    {
        selectFunction.fSubstring(field,pos,length);
        return this;
    }

    public SelectStack fLength(__MetaField field)
    {
        selectFunction.fLength(field);
        return this;
    }

    /**
     * Trim blank from both ends
     * @param field
     */
    public SelectStack fTrim(__MetaField field)
    {
        selectFunction.fTrim(field);
        return this;
    }

    /**
     * Trim char from both ends
     * @param field
     * @param ch
     */
    public SelectStack fTrim(__MetaField field,char ch)
    {
        selectFunction.fTrim(field,ch);
        return this;
    }

    public SelectStack fTrimLeading(__MetaField field,char ch)
    {
        selectFunction.fTrimLeading(field,ch);
        return this;
    }

    public SelectStack fTrimTrailing(__MetaField field,char ch)
    {
        selectFunction.fTrimTrailing(field,ch);
        return this;
    }

    public SelectStack fCurrentDate()
    {
        selectFunction.fCurrentDate();
        return this;
    }

    public SelectStack fCurrentTime()
    {
        selectFunction.fCurrentTime();
        return this;
    }

    public SelectStack fCurrentTimestamp()
    {
        selectFunction.fCurrentTimestamp();
        return this;
    }

    public SelectStack fYear(__MetaField field)
    {
        selectFunction.fYear(field);
        return this;
    }

    public SelectStack fMonth(__MetaField field)
    {
        selectFunction.fMonth(field);
        return this;
    }

    public SelectStack fDay(__MetaField field)
    {
        selectFunction.fDay(field);
        return this;
    }

    public SelectStack fHour(__MetaField field)
    {
        selectFunction.fHour(field);
        return this;
    }

    public SelectStack fMinute(__MetaField field)
    {
        selectFunction.fMinute(field);
        return this;
    }

    public SelectStack fSecond(__MetaField field)
    {
        selectFunction.fSecond(field);
        return this;
    }

    public <T extends Number> SelectStack fSum(__MetaField field, T value)
    {
        selectFunction.fSum(field,value);
        return this;
    }
    public SelectStack fSum(__MetaField field, __MetaField field2)
    {
        selectFunction.fSum(field,field2);
        return this;
    }

    public <T extends Number> SelectStack fDiff(__MetaField field, T value)
    {
        selectFunction.fDiff(field,value);
        return this;
    }
    public <T extends Number> SelectStack fDiff(T value,__MetaField field)
    {
        selectFunction.fDiff(value,field);
        return this;
    }
    public SelectStack fDiff(__MetaField field, __MetaField field2)
    {
        selectFunction.fDiff(field,field2);
        return this;
    }

    public <T extends Number> SelectStack fProd(__MetaField field, T value)
    {
        selectFunction.fProd(field,value);
        return this;
    }
    public SelectStack fProd(__MetaField field, __MetaField field2)
    {
        selectFunction.fProd(field,field2);
        return this;
    }

    public <T extends Number> SelectStack fDiv(__MetaField field, T value)
    {
        selectFunction.fDiv(field,value);
        return this;
    }
    public <T extends Number> SelectStack fDiv(T value,__MetaField field)
    {
        selectFunction.fDiv(value,field);
        return this;
    }
    public SelectStack fDiv(__MetaField field, __MetaField field2)
    {
        selectFunction.fDiv(field,field2);
        return this;
    }

    public SelectStack fMod(__MetaField field, Integer value)
    {
        selectFunction.fMod(field,value);
        return this;
    }
    public SelectStack fMod(Integer value,__MetaField field)
    {
        selectFunction.fMod(value,field);
        return this;
    }
    public SelectStack fMod(__MetaField field, __MetaField field2)
    {
        selectFunction.fMod(field,field2);
        return this;
    }

    public SelectStack fIsEmpty(__MetaField field)
    {
        selectFunction.fIsEmpty(field);
        return this;
    }

    public SelectStack fIsNotEmpty(__MetaField field)
    {
        selectFunction.fIsNotEmpty(field);
        return this;
    }

    public <T> SelectStack fIsMember(__MetaField<T> field,__MetaField<T> field2)
    {
        selectFunction.fIsMember(field,field2);
        return this;
    }

    public  <T> SelectStack fIsMember(T value,__MetaField<T> field)
    {
        selectFunction.fIsMember(value,field);
        return this;
    }

    public <T> SelectStack fIsNotMember(__MetaField<T> field,__MetaField<T> field2)
    {
        selectFunction.fIsNotMember(field,field2);
        return this;
    }

    public  <T> SelectStack fIsNotMember(T value,__MetaField<T> field)
    {
        selectFunction.fIsNotMember(value,field);
        return this;
    }

    protected void preparePredicate(Deque<SelectExpression> predicates, ArrayList<Predicate> resultPredicates)
    {
        if(predicates.isEmpty())
        {
            return;
        }
        ArrayList<SelectExpression> predicateAsList=new ArrayList<>();
        predicateAsList.add(predicates.getFirst());

        Predicate firstPredicate=preparePredicate(predicateAsList.iterator(),null);
        resultPredicates.add(firstPredicate);
    }

    protected Predicate preparePredicate(Iterator<SelectExpression> iterator, Predicate parentPredicate)
    {
        for(SelectExpression se;iterator.hasNext();)
        {
            se=iterator.next();
            if(se.getSelectExpressionType().equals(AND))
            {
                Predicate and=queryBuilder.and();
                preparePredicate(se.getExpressions().iterator(),and);
                if(null==parentPredicate)
                {
                    parentPredicate = and;
                }
                else
                {
                    parentPredicate.getExpressions().add(and);
                }
            }
            else
            if(se.getSelectExpressionType().equals(OR))
            {
                Predicate or=queryBuilder.or();
                preparePredicate(se.getExpressions().iterator(),or);
                if(null==parentPredicate)
                {
                    parentPredicate = or;
                }
                else
                {
                    parentPredicate.getExpressions().add(or);
                }
            }
            else
            {
                if(null==parentPredicate)
                {
                    throw new SelectException("Where or having predicate mandatory before conditional clause.");
                }
                Predicate condition=whereCondition(se);
                parentPredicate.getExpressions().add(condition);
            }
        }
        return parentPredicate;
    }

    protected void prepareExtQuery()
    {
        preparePredicate(wherePredicates,wherePredicateList);
        preparePredicate(havingPredicates,havingPredicateList);
    }

    public SelectStack resetWhere()
    {
        wherePredicates.clear();
        return this;
    }

    public SelectStack resetHaving()
    {
        havingPredicates.clear();
        return this;
    }

    public SelectStack resetSelection()
    {
        selections.clear();
        return this;
    }

    public SelectStack resetGroup()
    {
        groupList.clear();
        return this;
    }

    public SelectStack addWhere()
    {
        preparePredicate(wherePredicates,wherePredicateList);
        wherePredicates.clear();
        return this;
    }

    public SelectStack addHaving()
    {
        preparePredicate(havingPredicates,havingPredicateList);
        havingPredicates.clear();
        return this;
    }
}