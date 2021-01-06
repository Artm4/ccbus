package ccbus.dbtool.intermediate;

import ccbus.dbtool.intermediate.icodeimpl.ICodeNodeClass;
import ccbus.dbtool.intermediate.routineimpl.FieldCode;
import ccbus.dbtool.intermediate.routineimpl.ImportCode;
import ccbus.dbtool.intermediate.routineimpl.NodeCode;
import ccbus.dbtool.intermediate.routineparser.NodeParser;

import java.util.*;

public class RoutineCode extends NodeCode
{
    private ICodeNodeClass nodeClass;
    private String className;
    private Map<String, FieldCode > fieldsHashed=new HashMap<>();
    private List<FieldCode> fields=new ArrayList<>();
    private List<ImportCode> importCodes=new ArrayList<>();

    public RoutineCode(ICodeNodeClass nodeClass)
    {
        super(nodeClass);
        this.nodeClass = nodeClass;
    }

    public List<FieldCode> getFields() {
        return fields;
    }

    public List<ImportCode> getImportCodes()
    {
        return importCodes;
    }

    public Map<String, FieldCode> getFieldsHashed() {
        return fieldsHashed;
    }

    public void addField(FieldCode fieldCode)
    {
        fields.add(fieldCode);
        fieldsHashed.put(fieldCode.getFieldName(),fieldCode);
    }

    public ICodeNodeClass getNodeClass()
    {
        return nodeClass;
    }

    public void setNodeClass(ICodeNodeClass nodeClass)
    {
        this.nodeClass = nodeClass;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public void mergePrepend(RoutineCode routineCode)
    {
        fieldsHashed.putAll(routineCode.getFieldsHashed());

        List<FieldCode> fieldsM=new ArrayList<>();
        fieldsM.addAll(routineCode.getFields());
        fieldsM.addAll(fields);
        fields=fieldsM;

        List<ImportCode> importCodesM=new ArrayList<>();
        importCodesM.addAll(routineCode.getImportCodes());
        importCodesM.addAll(importCodes);
        importCodes=importCodesM;
    }

}
