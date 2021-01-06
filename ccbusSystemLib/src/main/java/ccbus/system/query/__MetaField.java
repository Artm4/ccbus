package ccbus.system.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import java.util.Collection;

public class __MetaField<T>
{
    private Class<T> classT;
    private String name;

    public __MetaClass parent;

    public Class<T> getClassT() {
        return classT;
    }

    public String getName() {
        return name;
    }

    public __MetaField(Class<T> classT, String name)
    {
        this.classT = classT;
        this.name = name;
    }

    public __MetaField(Class<T> classT, String name, __MetaClass parent)
    {
        this.classT = classT;
        this.name = name;
        this.parent=parent;
    }

    public __MetaClass getParent()
    {
        return parent;
    }

    public ParameterExpression<T> createParameterExpression(CriteriaBuilder queryBuilder, String suffix)
    {
        ParameterExpression<T> pp = queryBuilder.parameter(this.getClassT(),this.getName()+"_"+suffix);
        return pp;
    }

    //root.getJoins().iterator().next().getAttribute().toString()
    public <V> Expression<T> createExpression(Root<V> root)
    {
        Expression<T> x;
        if(parent!=null)
        {
            x = parent.path(name,root);
        }
        else
        {
          x = root.get(name);
        }
        return x;
    }


    public <V> Expression<Collection<T>> createExpressionCollection(Root<V> root)
    {
        Expression<Collection<T>> x;
        if(parent!=null)
        {
            x = parent.path(name,root);
        }
        else
        {
            x = root.get(name);
        }
        return x;
    }

}