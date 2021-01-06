package ccbus.system.query.select;


import ccbus.system.query.__MetaField;
import ccbus.system.query.predicate.IPredicate;

import javax.persistence.criteria.Expression;

import static ccbus.system.query.select.ParamCompletness.*;
import static ccbus.system.query.select.SelectExpressionType.*;

import java.util.ArrayList;
import java.util.List;

class SelectExpression
{

    IPredicate predicate;
    public Object value;
    __MetaField field;
    __MetaField fieldSecond;
    Expression func;

    List<Object> valueList=new ArrayList<>();
    ArrayList<__MetaField> fieldList=new ArrayList<>();

    ParamCompletness state;

    SelectExpressionType selectExpressionType;

    ArrayList<SelectExpression> expressions=new ArrayList();

    protected ConditionType conditionType;

    public ArrayList<SelectExpression> getExpressions()
    {
        return expressions;
    }

    public SelectExpression(ConditionType conditionType,SelectExpressionType selectExpressionType)
    {
        this.conditionType = conditionType;
        this.selectExpressionType = selectExpressionType;
    }

    public SelectExpression(ConditionType conditionType)
    {
        this.conditionType = conditionType;
        this.selectExpressionType = CONDITION;
    }

    public void reset()
    {
        predicate=null;
        value=null;
        field=null;
        fieldSecond=null;
        valueList.clear();
        fieldList.clear();
    }

    public void setConditionType(ConditionType conditionType)
    {
        this.conditionType = conditionType;
    }

    public SelectExpressionType getSelectExpressionType()
    {
        return selectExpressionType;
    }

    public ConditionType getConditionType()
    {
        return conditionType;
    }

    public void setState(ParamCompletness state)
    {
        this.state = state;
    }

    public ParamCompletness getState()
    {
        return state;
    }

    public List<Object> getValueList()
    {
        return valueList;
    }

    public boolean isState(ParamCompletness s)
    {
        if(null==state)
        {
            return false;
        }
        return state.equals(s);
    }

    protected boolean isExpresionReady(ParamCompletness param_completness)
    {
        boolean result;
        switch (param_completness)
        {
            case PRED_PAR:
                result=null!=field && predicate!=null;
                break;
            case PRED_VAL_PAR:
                result=null!=field && predicate!=null && value!=null;
                break;
            case PRED_FUN_PAR:
                result=null!=func && value!=null;
            default:
                result=null!=value && null!=predicate && null!=field;

        }

        return result;
    }

    protected boolean isExpresionReady()
    {
        ParamCompletness cState=null!=state ? state :  ParamCompletness.PRED_VAL_PAR;
        return isExpresionReady(cState);
    }

    public IPredicate getPredicate()
    {
        return predicate;
    }

    public void setPredicate(IPredicate predicate)
    {
        this.predicate = predicate;
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    public void setValueList(List<Object> value)
    {
        this.valueList = value;
    }

    public __MetaField getField()
    {
        return field;
    }

    public void setField(__MetaField field)
    {
        this.field = field;
    }

    public void setStateParForBinaryExpression()
    {
        if(this.isState(PRED_FUN))
        {
            this.setState(PRED_FUN_PAR);
        }
        else
        if(this.isState(PRED_PAR))
        {
            this.setState(PRED_PAR_PAR);
        }
        else
        {
            this.setState(PRED_PAR);
        }
    }

    public void setStateValueForBinaryExpression()
    {
        if(this.isState(PRED_FUN))
        {
            this.setState(PRED_FUN_VAL);
        }
        else
        if(this.isState(PRED_PAR))
        {
            this.setState(PRED_VAL_PAR);
        }
        else
        {
            this.setState(PRED_VAL);
        }
    }

    public void setStateFuncForFuncExpression()
    {
        this.setState(PRED_FUN);
    }

    public __MetaField getFieldSecond()
    {
        return fieldSecond;
    }

    public void setFieldSecond(__MetaField fieldSecond)
    {
        this.fieldSecond = fieldSecond;
    }

    public Expression getFunc()
    {
        return func;
    }

    public void setFunc(Expression func)
    {
        this.func = func;
    }

    @Override
    public String toString()
    {
        return "SelectExpression{" +
                "predicate=" + predicate +
                ", value=" + value +
                ", field=" + field +
                ", fieldSecond=" + fieldSecond +
                ", func=" + func +
                '}';
    }
}