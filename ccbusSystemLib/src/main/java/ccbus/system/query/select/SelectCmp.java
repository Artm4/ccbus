package ccbus.system.query.select;

import ccbus.system.query.__MetaField;
import ccbus.system.query.predicate.*;

import java.util.List;

public class SelectCmp<E,T>
{
    SelectStack<E,T> selectStack;

    IPredicate lessThan;
    IPredicate greaterThan;
    IPredicate greaterThanOrEqual;
    IPredicate lessThanOrEqual;
    IPredicate equal;
    IPredicate notEqual;
    IPredicate in;
    IPredicate isNull;
    IPredicate isNotNull;


    public SelectCmp(SelectStack<E, T> selectStack)
    {
        this.selectStack = selectStack;
        lessThan=new LessThan(selectStack.queryBuilder);
        greaterThan=new GreaterThan(selectStack.queryBuilder);
        greaterThanOrEqual=new GreaterThanOrEqual(selectStack.queryBuilder);
        lessThanOrEqual=new LessThanOrEqual(selectStack.queryBuilder);
        equal=new Equal(selectStack.queryBuilder);
        notEqual=new NotEqual(selectStack.queryBuilder);
        in=new In(selectStack.queryBuilder);
        isNull=new IsNull(selectStack.queryBuilder);
        isNotNull=new IsNotNull(selectStack.queryBuilder);
    }

    public <V extends Comparable> SelectStack cmpGeneric(IPredicate predicate,__MetaField field, V value)
    {
        SelectExpression selectExpression=selectStack.prepareConditionExpression();
        selectExpression.setField(field);
        selectExpression.setPredicate(predicate);
        selectExpression.setValue(value);

        return selectStack;
    }

    public <V extends Comparable> SelectStack cmpGeneric(IPredicate predicate,V value)
    {
        SelectExpression selectExpression=selectStack.prepareConditionExpression();
        selectExpression.setValue(value);
        selectExpression.setPredicate(predicate);
        return selectStack;
    }

    public SelectStack cmpGeneric(IPredicate predicate,__MetaField field)
    {
        SelectExpression selectExpression=selectStack.prepareConditionExpression();
        selectExpression.setField(field);
        selectExpression.setPredicate(predicate);
        selectExpression.setStateParForBinaryExpression();
        return selectStack;
    }


    public <V extends List> SelectStack cmpIn( __MetaField field,V value)
    {
        SelectExpression selectExpression=selectStack.prepareConditionExpression();
        selectExpression.setField(field);
        selectExpression.setPredicate(in);
        selectExpression.setValueList(value);
        return selectStack;
    }

    public <V extends List> SelectStack cmpIsNull( __MetaField field)
    {
        SelectExpression selectExpression=selectStack.prepareConditionExpression();
        selectExpression.setField(field);
        selectExpression.setPredicate(isNull);
        return selectStack;
    }

    public <V extends List> SelectStack cmpIsNotNull( __MetaField field)
    {
        SelectExpression selectExpression=selectStack.prepareConditionExpression();
        selectExpression.setField(field);
        selectExpression.setPredicate(isNotNull);
        return selectStack;
    }

    public <V extends Comparable> SelectStack cmpLessThan(__MetaField field, V value)
    {
        return cmpGeneric(lessThan,field,value);
    }

    public <V extends Comparable> SelectStack cmpLessThan(V value)
    {
        return cmpGeneric(lessThan,value);
    }

    public SelectStack cmpLessThan(__MetaField field)
    {
        return cmpGeneric(lessThan,field);
    }

    public <V extends Comparable> SelectStack cmpGreaterThan(__MetaField field, V value)
    {
        return cmpGeneric(greaterThan,field,value);
    }

    public <V extends Comparable> SelectStack cmpGreaterThan(V value)
    {
        return cmpGeneric(greaterThan,value);
    }

    public SelectStack cmpGreaterThan(__MetaField field)
    {
        return cmpGeneric(greaterThan,field);
    }


    public <V extends Comparable> SelectStack cmpEqual(__MetaField field, V value)
    {
        return cmpGeneric(equal,field,value);
    }

    public <V extends Comparable> SelectStack cmpEqual(V value)
    {
        return cmpGeneric(equal,value);
    }

    public SelectStack cmpEqual(__MetaField field)
    {
        return cmpGeneric(equal,field);
    }

    public <V extends Comparable> SelectStack cmpNotEqual(__MetaField field, V value)
    {
        return cmpGeneric(notEqual,field,value);
    }

    public <V extends Comparable> SelectStack cmpNotEqual(V value)
    {
        return cmpGeneric(notEqual,value);
    }

    public SelectStack cmpNotEqual(__MetaField field)
    {
        return cmpGeneric(notEqual,field);
    }


    public <V extends Comparable> SelectStack cmpGreaterThanOrEqual(__MetaField field, V value)
    {
        return cmpGeneric(greaterThanOrEqual,field,value);
    }

    public <V extends Comparable> SelectStack cmpGreaterThanOrEqual(V value)
    {
        return cmpGeneric(greaterThanOrEqual,value);
    }

    public SelectStack cmpGreaterThanOrEqual(__MetaField field)
    {
        return cmpGeneric(greaterThanOrEqual,field);
    }


    public <V extends Comparable> SelectStack cmpLessThanOrEqual(__MetaField field, V value)
    {
        return cmpGeneric(lessThanOrEqual,field,value);
    }

    public <V extends Comparable> SelectStack cmpLessThanOrEqual(V value)
    {
        return cmpGeneric(lessThanOrEqual,value);
    }

    public SelectStack cmpLessThanOrEqual(__MetaField field)
    {
        return cmpGeneric(lessThanOrEqual,field);
    }
}
