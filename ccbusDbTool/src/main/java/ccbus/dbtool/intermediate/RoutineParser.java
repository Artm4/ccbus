package ccbus.dbtool.intermediate;

import ccbus.dbtool.intermediate.icodeimpl.*;
import ccbus.dbtool.intermediate.routineimpl.ImportCode;
import ccbus.dbtool.intermediate.routineparser.ClassParser;
import ccbus.dbtool.intermediate.routineparser.EntityParser;
import ccbus.dbtool.intermediate.routineparser.EnumParser;
import ccbus.dbtool.intermediate.routineparser.FieldParser;

public class RoutineParser {
    protected FieldParser fieldParser=new FieldParser();

    private boolean base=false;

    public RoutineParser(){}
    public RoutineParser(boolean b){base=b;}

    public RoutineCode parse(ICodeNodeClass codeClass)
            throws Exception
    {
        boolean isEntity=
                codeClass.getNotationList().getChildren().size()>0 &&
                codeClass.getNotationList().containsNotationName("Entity");

        boolean isMetaEntity=
                codeClass.getNotationList().getChildren().size()>0 &&
                        codeClass.getNotationList().containsNotationName("MappedSuperclass");
        RoutineCode routineCode=null;

        if(!base && isMetaEntity)
        {
            throw new Exception("Class is not Entity");
        }
        if(isEntity || isMetaEntity)
        {
            routineCode=new EntityParser().parse(codeClass);
        }
        else if(codeClass.getClassType().equals("class"))
        {
            routineCode=new ClassParser().parse(codeClass);
        }
        else if(codeClass.getClassType().equals("enum"))
        {
            routineCode=new EnumParser().parse(codeClass);
        }
        else
        {
            throw new Exception("Not supported class type");
        }
        routineCode.setNodeClass(codeClass);
        routineCode.setClassName(codeClass.getClassName());
        return routineCode;
    }

    protected RoutineCode parseFieldList(ICodeNodeClass codeClass, RoutineCode routineCode)
            throws Exception
    {
        for(ICodeNode node:codeClass.getFieldList().getChildren())
        {
            ICodeNodeField field=(ICodeNodeField) node;
            parseField(routineCode,field);
        }

        for(ICodeNodeImport node:codeClass.getImportList().getList())
        {
            ImportCode importCode=new ImportCode(node);
            importCode.setImportDecl(node.getName());
            routineCode.getImportCodes().add(importCode);
        }
        return routineCode;
    }

    protected boolean parseField(RoutineCode entityCode,ICodeNodeField field)
            throws Exception
    {
        return false;
    }
}
