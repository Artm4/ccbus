package ccbus.dbtool.intermediate.routineimpl;

import ccbus.dbtool.intermediate.icodeimpl.*;
import ccbus.dbtool.intermediate.routineparser.IdentifierType;

public class RelationCode extends NodeCode{
    ICodeNodeField field;
    String mappedBy;
    String relationType;
    String fieldName;

    IdentifierType identifierType;

    // Class code that should be expanded once root tree done
    EntityCode classCode;

    public RelationCode(ICodeNodeField field,IdentifierType type)
    {
        super(field);
        this.identifierType =type;
        this.field=field;
    }

    public ICodeNodeField getField() {
        return field;
    }

    public String getMappedBy() {
        return mappedBy;
    }

    public void setMappedBy(String mappedBy) {
        this.mappedBy = mappedBy;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public IdentifierType getRelationship() {
        return identifierType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public EntityCode getClassCode() {
        return classCode;
    }

    public void setClassCode(EntityCode classCode) {
        this.classCode = classCode;
    }
}
