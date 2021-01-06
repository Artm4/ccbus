package ccbus.dbtool.backend.compiler.query;

import java.util.Map;

public class MetaEntity
{
    private String entityName;
    private Map<String,String> fields;

    public MetaEntity(String entityName, Map<String, String> fields)
    {
        this.entityName = entityName;
        this.fields = fields;
    }

    public String getEntityName()
    {
        return entityName;
    }

    public Map<String, String> getFields()
    {
        return fields;
    }
}
