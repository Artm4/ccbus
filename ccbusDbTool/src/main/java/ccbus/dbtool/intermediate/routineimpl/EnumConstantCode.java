package ccbus.dbtool.intermediate.routineimpl;

import ccbus.dbtool.intermediate.icodeimpl.ICodeNodeEnumConstant;

public class EnumConstantCode {
    private String name;
    ICodeNodeEnumConstant enumConstant;

    public EnumConstantCode(ICodeNodeEnumConstant enumConstant) {
        this.enumConstant=enumConstant;
        this.name = enumConstant.getFieldName();
    }

    public String getConstantName() {
        return name;
    }

    public void setConstantName(String name) {
        this.name = name;
    }
}
