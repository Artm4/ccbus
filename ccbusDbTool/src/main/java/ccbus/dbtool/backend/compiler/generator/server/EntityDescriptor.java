package ccbus.dbtool.backend.compiler.generator.server;


import ccbus.dbtool.backend.compiler.CodeGenerator;
import ccbus.dbtool.intermediate.routineimpl.EntityCode;
import ccbus.dbtool.intermediate.routineimpl.FieldCode;
import ccbus.dbtool.intermediate.routineimpl.ImportCode;
import ccbus.dbtool.intermediate.routineimpl.RelationCode;
import ccbus.dbtool.util.Tool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Meta
{
    public static PointMeta point;
    public static ShapeMeta shape;
}

class PointMeta
{
    public __MetaField<Long> id;
    public __MetaField<Integer> x;
    public  ShapeMeta shape;


}

class ShapeMeta
{
    public __MetaField<Long> id;
    public __MetaField<Integer> type;
    public  String name;
}



class __MetaField<T>
{
    private Class<T> classT;
    private String name;

    public Class<T> getClassT() {
        return classT;
    }

    public String getName() {
        return name;
    }
}

public class EntityDescriptor  extends CodeGenerator
{
    public static final String FILE_PREFIX="Meta_";
    Map<String,String> relationEntity=new HashMap<>();
    public static Map<String,String> castType=new HashMap<>();

    static {
        castType.put("int","Integer");
        castType.put("long","Long");
        castType.put("float","Float");
        castType.put("double","Double");
        castType.put("boolean","Boolean");

    }

    public EntityDescriptor(CodeGenerator parent)
    {
        super(parent);
    }

    EntityDescriptor()
    {

    }

    public void generate(EntityCode nodeCode) throws Exception
    {
        this.emitMeta(nodeCode);
    }

    private void emitMeta(EntityCode nodeCode) throws Exception
    {
        String className=FILE_PREFIX
                +nodeCode.getClassName();
        //this.setOut(Tool.instance().openFileWriter(metaFilePath));

        this.emitPackage(tool.getConfigParser().getMetaPackage());
        this.emitNewLine();

        this.emitImport("ccbus.system.query.__MetaField");
        this.emitNewLine();
        this.emitImport("ccbus.system.query.__MetaClass");
        this.emitNewLine();
// Let Entity define Date type Dojo, sql , or util
//        this.emitImport("java.sql.Date");
//        this.emitNewLine();
        this.emitImport("javax.persistence.ManyToOne");
        this.emitNewLine();
        this.emitImport("javax.persistence.OneToMany");
        this.emitNewLine();
        this.emitImport("javax.persistence.OneToOne");
        this.emitNewLine();
       // this.emitImport("org.joda.time.DateTime");
        for(ImportCode importCode:nodeCode.getImportCodes())
        {
            this.emit(importCode.getImportDecl());
            this.emitNewLine();
        }

        this.emitNewLine();
        this.emitNewLine();

        this.emit("public class "+className);
        this.emit("<T>");
        this.emit("  extends __MetaClass");
        this.emitNewLine();
        this.emit("{");
        this.indentAdd();
        this.emitNewLine();
        this.emitFieldList(nodeCode.getFields());
        this.emitRel(nodeCode.getRelFields());
        this.emitNewLine();

        this.emit("public "+className+"()");
        this.emitLn();
        this.emit("{");
        this.indentAdd();
        this.emitLn();
        this.emit("super();");
        this.emitRelInit(nodeCode.getRelFields());
        this.indentRemove();
        this.emitLn();
        this.emit("}");

        this.emitNewLine();
        this.emit("public "+className+"(__MetaClass parent,String path,String prevPath)");
        this.emitLn();
        this.emit("{");
        this.indentAdd();
        this.emitLn();
        this.emit("super(parent,path);");
        this.emitRelInitParent(nodeCode.getRelFields());
        this.indentRemove();
        this.emitLn();
        this.emit("}");

        this.indentRemove();
        this.emitNewLine();
        this.emit("}");
        this.emitNewLine();
    }

    private void emitFieldList(List<FieldCode> fieldCodes)
    {
        if(fieldCodes.size()==0)
        {
            return;
        }

        for(FieldCode node:fieldCodes)
        {
            if(node.getFieldName().equals("serialVersionUID"))
            {
                continue;
            }
            if(node.isComposite())
            {
                continue;
            }
            this.emit("public ");
            this.emit("__MetaField<"+cleanFieldType(node.getFieldType().getFullName())+">");
            this.emit(" ");
            this.emit(node.getFieldName());
            //
            this.emit(" = new __MetaField<"+cleanFieldType(node.getFieldType().getFullName())
                    +">("+cleanFieldType(node.getFieldType().getFullName())
                    +".class,"+"\""+node.getFieldName()+"\""
                    +",this)");
            //
            this.emit(";");
            this.emitNewLine();
        }
    }


    private void emitRel(List<RelationCode> relationCodes)
    {
        if(relationCodes.size()==0)
        {
            return;
        }
        for(RelationCode node:relationCodes)
        {
            this.emit("public ");
            this.emit("Meta_"+node.getRelationType());
            this.emit("<"+node.getRelationship().getName()+">");
            this.emit(" ");
            this.emit(this.fieldNameToRelName(node.getFieldName()));
            this.emit(";");
            this.emitNewLine();
        }
    }

    private String fieldNameToRelName(String fieldName)
    {
        return fieldName.substring(0,1).toUpperCase()+(fieldName.length()>2?fieldName.substring(1):"");
    }

    private void emitRelInit(List<RelationCode> relationCodes)
    {
        if(relationCodes.size()==0)
        {
            return;
        }
        for(RelationCode node:relationCodes)
        {
            this.emitLn();
            this.emit(this.fieldNameToRelName(node.getFieldName()));
            this.emit(" = new Meta_"+node.getRelationType()+"(this,\""+node.getFieldName()+"\""+",\"\")");
            this.emit(";");
        }
    }

    private void emitRelInitParent(List<RelationCode> relationCodes)
    {
        if(relationCodes.size()==0)
        {
            return;
        }
        for(RelationCode node:relationCodes)
        {
            this.emitLn();
            this.emit("if(!isRecursivePathClass(Meta_"+node.getRelationType()+".class))");
            this.emitLn();
            this.emit("{");
            this.indentAdd();
            this.emitLn();
            this.emit(this.fieldNameToRelName(node.getFieldName()));
            this.emit(" = new Meta_"+node.getRelationType()+"(this,\""+node.getFieldName()+"\""+",path)");
            this.emit(";");
            this.indentRemove();
            this.emitLn();
            this.emit("}");
        }
    }


    private String cleanFieldType(String typeName)
    {
        if(castType.containsKey(typeName))
        {
            return castType.get(typeName);
        }
        return typeName;
    }

    private <T> Class<T> get(__MetaField<T> fieldMeta)
    {
        return fieldMeta.getClassT();
    }
}
