package ccbus.dbtool.backend.compiler.generator.server;

import ccbus.dbtool.backend.compiler.CodeGenerator;
import ccbus.dbtool.intermediate.routineimpl.ClassCode;
import ccbus.dbtool.intermediate.routineimpl.EntityCode;
import ccbus.dbtool.intermediate.routineimpl.FieldCode;
import ccbus.dbtool.intermediate.routineimpl.RelationCode;
import ccbus.dbtool.util.Misc;
import ccbus.dbtool.util.Tool;

import java.util.HashMap;
import java.util.Map;

public class MetaDescriptor  extends CodeGenerator {
    public static final String FILE_NAME="Meta";
    public HashMap<String,Boolean> fields=new HashMap<String, Boolean>();

    public MetaDescriptor(CodeGenerator parent)
    {
        super(parent);
    }

    MetaDescriptor()
    {

    }

    public void generate(ClassCode metaCode,EntityCode entityCode) throws Exception
    {
        this.emitPackage(tool.getConfigParser().getMetaPackage());
        emitLn();
        emit("public class Meta");
        emitLn();
        emit("{");
        indentAdd();
        emitLn();

        for(FieldCode fieldCode:metaCode.getFields())
        {
            emitClean(fieldCode.getFieldType().getFullName(),fieldCode.getFieldName().replace("Meta_",""));
            fields.put(fieldCode.getFieldType().getFullName(),true);
        }

        emitField(entityCode.getClassName());

        // It could be either done by root entity or by iterative compilation
//        for(RelationCode node:entityCode.getRelFields())
//        {
//            emitField(node.getClassCode().getClassName());
//        }

        indentRemove();
        emitLn();
        emit("}");
    }

    private boolean hasField(String fieldName)
    {
        return fields.containsKey(fieldName);
    }

    private void emitClean(String type,String name)
    {
        emit("public static ");
        emit(type);
        emit(" ");
        emit(name);
        emit(" = new "+type+"()");
        emit(";");
        emitLn();
    }

    private void emitField(String type)
    {
        String fieldName=FILE_NAME+"_"+type;
        if(hasField(fieldName))
        {
            return;
        }
        emit("public static ");
        emit(FILE_NAME+"_"+type);
        emit(" ");
        //emit(Misc.firstLowerCase(type));
        emit(type);
        emit(" = new "+FILE_NAME+"_"+type+"()");
        emit(";");
        emitLn();
    }
}
