package ccbus.dbtool.intermediate.routineimpl;

import ccbus.dbtool.intermediate.*;
import ccbus.dbtool.intermediate.icodeimpl.ICodeNodeClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityCode extends RoutineCode {
    private Map<String, RelationCode> relFieldsHashed=new HashMap<>();
    private List<RelationCode> relFields=new ArrayList<>();

    public EntityCode(ICodeNodeClass nodeClass)
    {
        super(nodeClass);
    }

    public List<RelationCode> getRelFields() {
        return relFields;
    }

    public Map<String, RelationCode> getRelFieldsHashed() {
        return relFieldsHashed;
    }

    public void addRelField(RelationCode fieldCode)
    {
        relFields.add(fieldCode);
        relFieldsHashed.put(fieldCode.getFieldName(),fieldCode);
    }

    public void mergePrepend(RoutineCode routineCode)
    {
        super.mergePrepend(routineCode);
        EntityCode entityCode=(EntityCode)routineCode;
        relFieldsHashed.putAll(entityCode.getRelFieldsHashed());

        List<RelationCode> relFieldsM=new ArrayList<>();
        relFieldsM.addAll(entityCode.getRelFields());
        relFieldsM.addAll(relFields);
        relFields=relFieldsM;
    }
}
