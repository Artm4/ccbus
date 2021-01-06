package ccbus.system.query.select;

import ccbus.system.query.__MetaField;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.Collection;

public class SelectFunction<E, F>
{
    private SelectStack<E, F> selectStack;
    private CriteriaBuilder queryBuilder;
    private Root<E> root;

    SelectFunction(SelectStack<E, F> selectStack)
    {
        this.selectStack = selectStack;
        queryBuilder=selectStack.getQueryBuilder();
        root=selectStack.getRoot();
    }

    <V> void fCount(__MetaField<V> field)
    {
        Expression e = queryBuilder.count(selectStack.createExpression(field));
        selectStack.setFunctionExpression(e);
    }

    <V> void fCountDistinct(__MetaField<V>  field)
    {
        Expression e = queryBuilder.countDistinct(selectStack.createExpression(field));
        selectStack.setFunctionExpression(e);
    }

    <V extends Number> void fSum(__MetaField<V> field)
    {
        Expression e = queryBuilder.sum(selectStack.createExpression(field));
        selectStack.setFunctionExpression(e);
    }

    void fSumAsLong(__MetaField<Integer> field)
    {
        Expression e = queryBuilder.sumAsLong(selectStack.createExpression(field));
        selectStack.setFunctionExpression(e);
    }

     void fSumAsDouble(__MetaField<Float> field)
    {
        Expression e = queryBuilder.sumAsDouble(selectStack.createExpression(field));
        selectStack.setFunctionExpression(e);
    }

    <V extends Number> void fAvg(__MetaField<V> field)
    {
        Expression e = queryBuilder.avg(selectStack.createExpression(field));
        selectStack.setFunctionExpression(e);
    }

    <V extends Number> void fMin(__MetaField<V> field)
    {
        Expression e = queryBuilder.min(selectStack.createExpression(field));
        selectStack.setFunctionExpression(e);
    }

    <V extends Comparable<V>> void fLeast(__MetaField<V> field)
    {
        Expression e = queryBuilder.least(selectStack.createExpression(field));
        selectStack.setFunctionExpression(e);
    }

    <V extends Number> void fMax(__MetaField<V> field)
    {
        Expression e = queryBuilder.max(selectStack.createExpression(field));
        selectStack.setFunctionExpression(e);
    }

    <V extends Comparable<V>> void fGreatest(__MetaField<V> field)
    {
        Expression e = queryBuilder.greatest(selectStack.createExpression(field));
        selectStack.setFunctionExpression(e);
    }

    void fLike(__MetaField<String> field, String value)
    {
        Expression e = queryBuilder.like(selectStack.createExpression(field),value);
        selectStack.setFunctionExpression(e);
    }

    void fConcat(__MetaField<String> field, String value)
    {
        Expression e = queryBuilder.concat(selectStack.createExpression(field),value);
        selectStack.setFunctionExpression(e);
    }

    void fConcat(__MetaField<String> field, __MetaField<String> field2)
    {
        Expression e = queryBuilder.concat(selectStack.createExpression(field),selectStack.createExpression(field2));
        selectStack.setFunctionExpression(e);
    }

    void fConcat(__MetaField<String> field, String value, __MetaField<String> field2)
    {
        Expression e = queryBuilder.concat(queryBuilder.concat(selectStack.createExpression(field),value),selectStack.createExpression(field2));
        selectStack.setFunctionExpression(e);
    }

    void fSubstring(__MetaField<String> field, int pos)
    {
        Expression e = queryBuilder.substring(selectStack.createExpression(field),pos);
        selectStack.setFunctionExpression(e);
    }

    void fSubstring(__MetaField<String> field, int pos, int length)
    {
        Expression e = queryBuilder.substring(selectStack.createExpression(field),pos,length);
        selectStack.setFunctionExpression(e);
    }

    void fLength(__MetaField<String> field)
    {
        Expression e = queryBuilder.length(selectStack.createExpression(field));
        selectStack.setFunctionExpression(e);
    }

    void fTrim(__MetaField<String> field)
    {
        Expression e = queryBuilder.trim(selectStack.createExpression(field));
        selectStack.setFunctionExpression(e);
    }

    void fTrim(__MetaField<String> field,char ch)
    {
        Expression e = queryBuilder.trim(ch,selectStack.createExpression(field));
        selectStack.setFunctionExpression(e);
    }

    void fTrimLeading(__MetaField<String> field,char ch)
    {
        Expression e = queryBuilder.trim(CriteriaBuilder.Trimspec.LEADING,ch,selectStack.createExpression(field));
        selectStack.setFunctionExpression(e);
    }

    void fTrimTrailing(__MetaField<String> field,char ch)
    {
        Expression e = queryBuilder.trim(CriteriaBuilder.Trimspec.TRAILING,ch,selectStack.createExpression(field));
        selectStack.setFunctionExpression(e);
    }

    void fCurrentDate()
    {
        Expression e = queryBuilder.currentDate();
        selectStack.setFunctionExpression(e);
    }

    void fCurrentTime()
    {
        Expression e = queryBuilder.currentTime();
        selectStack.setFunctionExpression(e);
    }

    void fCurrentTimestamp()
    {
        Expression e = queryBuilder.currentTimestamp();
        selectStack.setFunctionExpression(e);
    }

    private void fPartDateTime(__MetaField field,String part)
    {
        Expression e = queryBuilder.function(part, Integer.class, selectStack.createExpression(field));
        selectStack.setFunctionExpression(e);
    }

    void fYear(__MetaField field)
    {
        fPartDateTime(field,"year");
    }

    void fMonth(__MetaField field)
    {
        fPartDateTime(field,"month");
    }

    void fDay(__MetaField field)
    {
        fPartDateTime(field,"day");
    }

    void fHour(__MetaField field)
    {
        fPartDateTime(field,"hour");
    }

    void fMinute(__MetaField field)
    {
        fPartDateTime(field,"minute");
    }

    void fSecond(__MetaField field)
    {
        fPartDateTime(field,"second");
    }

    <T extends Number> void fSum(__MetaField<T> field, T value)
    {
        Expression e = queryBuilder.sum(selectStack.createExpression(field),value);
        selectStack.setFunctionExpression(e);
    }
    <T extends Number> void fSum(__MetaField<T> field, __MetaField<T> field2)
    {
        Expression e = queryBuilder.sum(selectStack.createExpression(field),selectStack.createExpression(field2));
        selectStack.setFunctionExpression(e);
    }

    <T extends Number> void fDiff(__MetaField<T> field, T value)
    {
        Expression e = queryBuilder.diff(selectStack.createExpression(field),value);

        selectStack.setFunctionExpression(e);
    }
    <T extends Number> void fDiff(T value,__MetaField<T> field)
    {
        Expression e = queryBuilder.diff(value,selectStack.createExpression(field));

        selectStack.setFunctionExpression(e);
    }
    <T extends Number> void fDiff(__MetaField<T> field, __MetaField<T> field2)
    {
        Expression e = queryBuilder.diff(selectStack.createExpression(field),selectStack.createExpression(field2));
        selectStack.setFunctionExpression(e);
    }

    <T extends Number> void fProd(__MetaField<T> field, T value)
    {
        Expression e = queryBuilder.prod(selectStack.createExpression(field),value);

        selectStack.setFunctionExpression(e);
    }
    <T extends Number> void fProd(__MetaField<T> field, __MetaField<T> field2)
    {
        Expression e = queryBuilder.prod(selectStack.createExpression(field),selectStack.createExpression(field2));
        selectStack.setFunctionExpression(e);
    }

    <T extends Number> void fDiv(__MetaField<T> field, T value)
    {
        Expression e = queryBuilder.quot(selectStack.createExpression(field),value);
        selectStack.setFunctionExpression(e);
    }
    <T extends Number> void fDiv(T value,__MetaField<T> field)
    {
        Expression e = queryBuilder.quot(value,selectStack.createExpression(field));
        selectStack.setFunctionExpression(e);
    }
    <T extends Number> void fDiv(__MetaField<T> field, __MetaField<T> field2)
    {
        Expression e = queryBuilder.quot(selectStack.createExpression(field),selectStack.createExpression(field));
        selectStack.setFunctionExpression(e);
    }

    void fMod(__MetaField<Integer> field, Integer value)
    {
        Expression e = queryBuilder.mod(selectStack.createExpression(field),value);
        selectStack.setFunctionExpression(e);
    }
    void fMod(Integer value,__MetaField<Integer> field)
    {
        Expression e = queryBuilder.mod(value,selectStack.createExpression(field));
        selectStack.setFunctionExpression(e);
    }
    void fMod(__MetaField<Integer> field, __MetaField<Integer> field2)
    {
        Expression e = queryBuilder.mod(selectStack.createExpression(field),selectStack.createExpression(field2));
        selectStack.setFunctionExpression(e);
    }

    void fIsEmpty(__MetaField<Collection> field)
    {
        Expression e = queryBuilder.isEmpty(selectStack.createExpression(field));
        selectStack.setFunctionExpression(e);
    }

    void fIsNotEmpty(__MetaField<Collection> field)
    {
        Expression e = queryBuilder.isNotEmpty(selectStack.createExpression(field));
        selectStack.setFunctionExpression(e);
    }

    <T> void fIsMember(__MetaField<T> field,__MetaField<T> field2)
    {
        Expression e = queryBuilder.isMember(selectStack.createExpression(field),
                field2.createExpressionCollection(root));
        selectStack.setFunctionExpression(e);
    }

     <T> void fIsMember(T value,__MetaField<T> field)
    {
        Expression e = queryBuilder.isMember(value,field.createExpressionCollection(root));
        selectStack.setFunctionExpression(e);
    }

    <T> void fIsNotMember(__MetaField<T> field,__MetaField<T> field2)
    {
        Expression e = queryBuilder.isNotMember(selectStack.createExpression(field),
                field2.createExpressionCollection(root));
        selectStack.setFunctionExpression(e);
    }

     <T> void fIsNotMember(T value,__MetaField<T> field)
    {
        Expression e = queryBuilder.isNotMember(value,field.createExpressionCollection(root));
        selectStack.setFunctionExpression(e);
    }
}