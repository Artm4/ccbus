package ccbus.dbtool.intermediate.routineparser;

import ccbus.dbtool.intermediate.RoutineCode;
import ccbus.dbtool.intermediate.RoutineParser;
import ccbus.dbtool.intermediate.icodeimpl.ICodeNodeClass;
import ccbus.dbtool.intermediate.icodeimpl.ICodeNodeField;
import ccbus.dbtool.intermediate.routineimpl.ClassCode;
import ccbus.dbtool.intermediate.routineimpl.FieldCode;

public class ClassParser extends RoutineParser
{
    public RoutineCode parse(ICodeNodeClass codeClass)
            throws Exception
    {
        return parseFieldList(codeClass,new ClassCode(codeClass));
    }

    protected boolean parseField(RoutineCode routineCode,ICodeNodeField field)
            throws Exception
    {
        FieldCode fieldCode=fieldParser.parse(field);
        routineCode.addField(fieldCode);
        return true;
    }
}
