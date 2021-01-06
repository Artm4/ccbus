package ccbus.dbtool.intermediate.routineimpl;

import ccbus.dbtool.intermediate.icodeimpl.*;

public class FieldCode extends NodeCode
{
    private ICodeNodeField field;
    private TypeForm typeForm;
    private String fieldName;

    private boolean isComposite=false;

    private ICodeNodeName fieldType;
    private ICodeNodeName templateParamFirst;
    private ICodeNodeName templateParamSecond;

    public FieldCode(ICodeNodeField field,TypeForm typeForm) {
        super(field);
        this.field = field;
        this.typeForm=typeForm;
    }

    public ICodeNodeField getField() {
        return field;
    }

    public void setField(ICodeNodeField field) {
        this.field = field;
    }

    public TypeForm getTypeForm() {
        return typeForm;
    }

    public void setTypeForm(TypeForm typeForm) {
        this.typeForm = typeForm;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public boolean isComposite() {
        return isComposite;
    }

    public void setComposite(boolean composite) {
        isComposite = composite;
    }

    public ICodeNodeName getFieldType() {
        return fieldType;
    }

    public void setFieldType(ICodeNodeName fieldType) {
        this.fieldType = fieldType;
    }

    public ICodeNodeName getTemplateParamFirst() {
        return templateParamFirst;
    }

    public void setTemplateParamFirst(ICodeNodeName templateParamFirst) {
        this.templateParamFirst = templateParamFirst;
    }

    public ICodeNodeName getTemplateParamSecond() {
        return templateParamSecond;
    }

    public void setTemplateParamSecond(ICodeNodeName templateParamSecond) {
        this.templateParamSecond = templateParamSecond;
    }
}
