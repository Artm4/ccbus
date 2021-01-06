package ccbus.dbtool.backend.compiler.query;

import java.util.HashMap;
import java.util.Map;

public class MetaQuery
{
    MetaEntity from;
    Map<String,MetaEntity> joins=new HashMap<>();
    public MetaQuery(MetaEntity...entities)
    {
        if(0==entities.length)
        {
            return;
        }
        from=entities[0];
        for(int i=1;i<entities.length;i++)
        {
            joins.put(entities[i].getEntityName(),entities[i]);
        }
    }

    public MetaEntity getFrom()
    {
        return from;
    }

    public MetaEntity getJoinByName(String name)
    {
        return joins.get(name);
    }
}
