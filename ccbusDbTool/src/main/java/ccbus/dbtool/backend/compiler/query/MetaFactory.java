package ccbus.dbtool.backend.compiler.query;

import java.util.Map;

public class MetaFactory
{
    public static MetaQuery createMeta(MetaEntity...entities)
    {
        return new MetaQuery(entities);
    }

    public static MetaEntity createMetaEntity(String entityName, Map<String, String> fields)
    {
        return new MetaEntity(entityName,fields);
    }
}
