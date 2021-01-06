package ccbus.tool.translator.java2reactserverjava;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.java.*;
import ccbus.tool.util.java.Tool;

public class AllowedType
{
    private Tool tool;

    /* Custom type*/
    final String arrayType="ArrayList";
    final String objectType="HashMap";
    final String functionType="Function";
    final String functionBiType="BiFunction";
    final String dateType="DateTime";
    final String stringType="String";

    /* Prop type */
    final String objectPropType[]={"PropTypes","object"};
    final String arrayPropType[]={"PropTypes","array"};
    final String functionPropType[]={"PropTypes","func"};
    final String stringPropType[]={"PropTypes","string"};

    final String booleanPropType[]={"PropTypes","boolean"};
    final String numberPropType[]={"PropTypes","number"};

    public AllowedType(Tool t)
    {
        tool=t;
    }

    public Node translate(ASTFieldDeclaration node)
    {
        Node nodeResult;
        ASTReferenceType referenceType=
                (ASTReferenceType)
                node.findFirstDownById(JavaParserTreeConstants.JJTREFERENCETYPE,2);
        ASTBracket bracket=(ASTBracket)node.findFirstDownById(JavaParserTreeConstants.JJTBRACKET,3);
        if(null!=bracket)
        {
            nodeResult=buildPropType(arrayPropType);
        }
        else
        if(null!=referenceType)
        {
            nodeResult=translate(referenceType);
        }
        else
        {
            ASTPrimitiveType primitiveType=
                    (ASTPrimitiveType)
                    node.findFirstDownById(JavaParserTreeConstants.JJTPRIMITIVETYPE,2);
            nodeResult=translate(primitiveType);
        }
        return nodeResult;
    }

    private Node translate(ASTPrimitiveType node)
    {
        String[] propType={};
        switch (node.jjtGetFirstToken().kind)
        {
            case JavaParserConstants.BOOLEAN:
                propType = booleanPropType;
                break;
            case JavaParserConstants.CHAR:
                propType = stringPropType;
                break;
            case JavaParserConstants.STRING_LITERAL:
                propType = stringPropType;
                break;
            case JavaParserConstants.BYTE:
            case JavaParserConstants.SHORT:
            case JavaParserConstants.INT:
            case JavaParserConstants.LONG:
            case JavaParserConstants.FLOAT:
            case JavaParserConstants.DOUBLE:
                propType = numberPropType;
                break;
        }
        return buildPropType(propType);
    }
    private Node translate(ASTReferenceType node)
    {
        String[] propType={};
        ASTBracket bracket=(ASTBracket)node.findFirstDownById(JavaParserTreeConstants.JJTBRACKET,1);
        ASTClassOrInterfaceType classOrInterfaceType=(ASTClassOrInterfaceType)node.findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACETYPE,1);
        if(null!=bracket)
        {
            propType=arrayPropType;
        }
        else
        if(null!=classOrInterfaceType)
        {
            ASTIdentifier identifier=
                    (ASTIdentifier)
                    classOrInterfaceType.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,1);
            String name=identifier.image();
            if(name.equals(arrayType))
            {
                propType=arrayPropType;
            }
            else
            if(name.equals(objectType)||name.equals(dateType))
            {
                propType=objectPropType;
            }
            else
            if(name.equals(functionType)|| name.equals(functionBiType))
            {
                propType=functionPropType;
            }
            else
            if(name.equals(stringType))
            {
                propType=stringPropType;
            }
            else
            {
                // should allow only client shared types, not java build in types.
                propType=objectPropType;
            }
        }
        else
        {
            // should throw error
            tool.errorTranslate(classOrInterfaceType,"Cannot resolve prop type");
        }

        return buildPropType(propType);
    }


    private Node buildPropType(String[] propType)
    {
        ASTName name=new ASTName(JavaParserTreeConstants.JJTNAME);
        for(int i=0;i<propType.length;i++)
        {
            ASTIdentifier identifier= tool.createNodeIdentifier(propType[i]);
            name.add(identifier);
            if(i<propType.length-1)
            {
                name.add(tool.createNodeDot());
            }
        }
        return name;
    }

}
