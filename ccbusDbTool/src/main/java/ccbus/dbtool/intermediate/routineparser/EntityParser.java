package ccbus.dbtool.intermediate.routineparser;

import ccbus.dbtool.intermediate.*;
import ccbus.dbtool.intermediate.icodeimpl.*;
import ccbus.dbtool.intermediate.icodeimpl.ICodeNodeTypeId;
import ccbus.dbtool.intermediate.routineimpl.*;

import java.util.Arrays;
import java.util.Collection;

import static ccbus.dbtool.intermediate.routineparser.IdentifierType.*;

public class EntityParser extends RoutineParser {

    private static final Collection<String> skip=
            Arrays.asList(TRANSIENT.getName()
            );

    private static final Collection<String> relation=
            Arrays.asList(MANY_TO_ONE.getName(),
                    MANY_TO_ONE.getName(),
                    ONE_TO_MANY.getName(),
                    ONE_TO_ONE.getName()
            );


    public RoutineCode parse(ICodeNodeClass codeClass)
            throws Exception
    {
        return parseFieldList(codeClass,new EntityCode(codeClass));
    }

    protected boolean parseField(RoutineCode routineCode,ICodeNodeField field)
            throws Exception
    {
        EntityCode entityCode=(EntityCode) routineCode;
        RelationCode relationCode=null;
        for(ICodeNode node:field.getNotationList().getChildren())
        {
            ICodeNodeNotation notation=(ICodeNodeNotation) node;
            // skip Transient...
            if(skip.contains(notation.getNotationName()))
            {
                return false;
            }
            if(notation.getNotationName().equals(ONE_TO_MANY.getName()))
            {
                // Put back One to many relation
                //return false;
            }
            if(relation.contains(notation.getNotationName())) {
                relationCode = parseRelField(field, notation);
            }
        }

        if(null!=relationCode)
        {
            entityCode.addRelField(relationCode);
        }
        else
        {
            FieldCode fieldCode=fieldParser.parse(field);
            // composite field should be skipped, because it should be relational
            if(fieldCode.isComposite())
            {
                return false;
            }
            entityCode.addField(fieldCode);
        }
        return true;
    }

    private RelationCode parseRelField(ICodeNodeField field,ICodeNodeNotation relNotation)
            throws Exception
    {
        RelationCode relationCode=new RelationCode(field,
                getRelationByName(relNotation.getNotationName())
        );
        relationCode.setFieldName(field.getFieldName());
        for(ICodeNode node:relNotation.getChildren())
        {
            ICodeNodeKeyValue keyValue=(ICodeNodeKeyValue)node;
            if("mappedBy".contains(keyValue.getKey()))
            {
                relationCode.setMappedBy(keyValue.getValue());
            }
        }
        ITCodeNodeList nodeList=field.getFieldType().getTemplateParams();
        if(nodeList.getChildren().size()>1)
        {
            throw new Exception("Generic definition should be single parameter for "+field.getFieldName());
        }

        if(nodeList.getChildren().size()>0)
        {
            ICodeNodeTypeId templateType = (ICodeNodeTypeId) nodeList.getChildren().get(0);
            if (templateType.hasTemplate()) {
                throw new Exception("Generic parameter type shouldn't be generic for " + field.getFieldName());
            }
            if(relNotation.getNotationName().equals(ONE_TO_ONE.getName()))
            {
                throw new Exception("Should be 'one to many' or 'many to one' for " + field.getFieldName());
            }
            relationCode.setRelationType(templateType.getTypeName());
        }
        else
        {
            if(relNotation.getNotationName().equals(ONE_TO_MANY.getName()))
            {
                throw new Exception("Should be 'many to one' or 'one to one' for " + field.getFieldName());
            }
            relationCode.setRelationType(field.getFieldType().getTypeName());
        }

        return relationCode;
    }
}
