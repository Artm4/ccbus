package ccbus.dbtool.intermediate.routineparser;

import ccbus.dbtool.intermediate.icodeimpl.*;
import ccbus.dbtool.intermediate.routineimpl.*;

import java.util.Arrays;
import java.util.Collection;

import static ccbus.dbtool.intermediate.routineparser.IdentifierType.*;

public class FieldParser
{
    private static final Collection<String> compositeCollection=
        Arrays.asList(
            SET.getName(),COLLECTION.getName()
        );

    private static final Collection<String> compositeHash=
        Arrays.asList(
            HASH.getName(),HASH_MAP.getName()
        );

    private static final Collection<String> scalarInt=
        Arrays.asList(
                OLONG.getName(),OINTEGER.getName(),
                LONG.getName(),INT.getName()
        );

    private static final Collection<String> scalarFloat=
        Arrays.asList(
                OFLOAT.getName(),ODOUBLE.getName(),
                FLOAT.getName(),DOUBLE.getName()
        );

    private static final Collection<String> date=
            Arrays.asList(
                    DATE.getName(),DATE_TIME.getName()
            );


    public FieldCode parse(ICodeNodeField field)
            throws Exception
    {
        String fieldTypeIdentifier=field.getFieldType().getTypeNameLast();
        TypeForm typeForm;
        if(compositeCollection.contains(fieldTypeIdentifier))
        {
            typeForm=TypeForm.COLLECTION;
        }
        else if(compositeHash.contains(fieldTypeIdentifier))
        {
            typeForm=TypeForm.HASH;
        }
        else if(scalarInt.contains(fieldTypeIdentifier))
        {
            typeForm=TypeForm.SCALAR_INT;
        }
        else if(scalarFloat.contains(fieldTypeIdentifier))
        {
            typeForm=TypeForm.SCALAR_FLOAT;
        }
        else if(date.contains(fieldTypeIdentifier))
        {
            typeForm=TypeForm.DATE;
        }
        else
        {
            // class,enum,entity...
            typeForm=TypeForm.CLASS;
        }
        FieldCode fieldCode=new FieldCode(field,typeForm);
        fieldCode.setFieldName(field.getFieldName());
        fieldCode.setFieldType(field.getFieldType().getName());

        if(field.getFieldType().hasTemplate())
        {
            ITCodeNodeList params=field.getFieldType().getTemplateParams();
            fieldCode.setComposite(true);

            if(typeForm==TypeForm.COLLECTION)
            {
                if(params.getChildren().size()==1) {
                    fieldCode.setTemplateParamFirst(
                            ((ICodeNodeTypeId) params.getChildren().get(0)).getName()
                    );
                }
                else
                {
                    throw new Exception("Collection should have one template params for "+field.getFieldName());
                }
            }

            if(typeForm==TypeForm.HASH)
            {
                if(params.getChildren().size()==2) {
                    fieldCode.setTemplateParamFirst(
                            ((ICodeNodeTypeId) params.getChildren().get(0)).getName()
                    );

                    fieldCode.setTemplateParamSecond(
                            ((ICodeNodeTypeId) params.getChildren().get(1)).getName()
                    );
                }
                else
                {
                    throw new Exception("Hash should have two template params");
                }
            }

            if(params.getChildren().size()>2)
            {
                throw new Exception("Not supporting more than 2 template params");
            }
        }
        return fieldCode;
    }
}
