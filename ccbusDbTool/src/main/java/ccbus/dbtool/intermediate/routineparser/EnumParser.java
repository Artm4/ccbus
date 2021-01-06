package ccbus.dbtool.intermediate.routineparser;

import ccbus.dbtool.intermediate.ICodeNode;
import ccbus.dbtool.intermediate.RoutineCode;
import ccbus.dbtool.intermediate.RoutineParser;
import ccbus.dbtool.intermediate.icodeimpl.ICodeNodeClass;
import ccbus.dbtool.intermediate.icodeimpl.ICodeNodeEnumConstant;
import ccbus.dbtool.intermediate.routineimpl.EnumCode;
import ccbus.dbtool.intermediate.routineimpl.EnumConstantCode;

public class EnumParser extends RoutineParser {
    public RoutineCode parse(ICodeNodeClass codeClass)
            throws Exception
    {
        EnumCode enumCode=new EnumCode(codeClass);
        for(ICodeNode node:codeClass.getFieldList().getChildren())
        {
            ICodeNodeEnumConstant field=(ICodeNodeEnumConstant) node;
            this.parseField(enumCode,field);
        }
        return (RoutineCode) enumCode;
    }

    protected boolean parseField(EnumCode enumCode, ICodeNodeEnumConstant field)
            throws Exception
    {
        EnumConstantCode enumConstantCode=new EnumConstantCode(field);
        enumConstantCode.setConstantName(field.getFieldName());
        enumCode.addConstant(enumConstantCode);
        return true;
    }
}
