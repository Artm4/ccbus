package ccbus.dbtool.util;

import ccbus.dbtool.backend.compiler.util.PrintStream;
import ccbus.dbtool.intermediate.RoutineCode;
import ccbus.dbtool.intermediate.routineimpl.EntityCode;
import ccbus.dbtool.intermediate.routineimpl.FieldCode;
import ccbus.dbtool.intermediate.routineimpl.RelationCode;

import java.io.OutputStream;


public class EntityPrinter
{
    private PrintStream printer;
    public EntityPrinter(OutputStream ps)
    {
        printer=new PrintStream(ps);
    }

    public void print(EntityCode entityCode)
    {
        printer.emitLn();
        printer.emit("=====Entity "+entityCode.getClassName()+"=====");
        printer.indentAdd();
        printer.emitLn();

        printFields(entityCode);

        printRelFields(entityCode);

        printer.indentRemove();
        printer.emitLn();
    }

    private void printFields(EntityCode entityCode)
    {
        printer.emit("<<<Fields>>>");
        printer.indentAdd();
        printer.emitLn();
        for(FieldCode field:entityCode.getFields())
        {
            String fieldDecl=String.format("%s %s",field.getFieldType().getFullName(),field.getFieldName());
            printer.emit(fieldDecl);
            printer.emitLn();
        }
        printer.indentRemove();
    }

    private void printRelFields(EntityCode entityCode)
    {
        printer.emitLn();
        printer.emit("***Relations***");
        printer.indentAdd();
        printer.emitLn();
        for(RelationCode relCode:entityCode.getRelFields())
        {
            String fieldDecl=String.format("%s %s [%s]",relCode.getRelationType(),relCode.getFieldName(),relCode.getRelationship().getName());
            printer.emit(fieldDecl);
            printer.emitLn();
        }

        for(RelationCode relCode:entityCode.getRelFields())
        {

            if(null!=relCode.getClassCode())
            {
                print(relCode.getClassCode());
            }
        }
        printer.indentRemove();
       // printer.emitLn();
    }

}
