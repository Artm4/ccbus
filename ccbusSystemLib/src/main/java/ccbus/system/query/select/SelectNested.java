package ccbus.system.query.select;

import ccbus.system.query.__MetaField;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Supplier;

public class SelectNested<E,T> extends SelectBase<E,T> {

    public SelectNested(Class<E> rootT, Class<T> resT)
    {
        super(rootT, resT);
    }

    @Override
    protected void prepareExtQuery()
    {

    }
}
