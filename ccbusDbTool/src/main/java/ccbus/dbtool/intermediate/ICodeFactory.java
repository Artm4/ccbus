package ccbus.dbtool.intermediate;

import ccbus.dbtool.intermediate.icodeimpl.*;

public class ICodeFactory
{
    public static ICode createICode()
    {
        ICode iCode=new ICodeImpl();
        iCode.setRoot(ICodeFactory.createICodeNode(ICodeNodeTypeImpl.CLASS_ROUTINE));
        return iCode;
    }

    public static ICodeNode createICodeNode(ICodeNodeType type)
    {
        return new ICodeNodeImpl(type);
    }

    public static ICodeNodeClass createICodeNodeClass()
    {
        return new ICodeNodeClass(ICodeNodeTypeImpl.CLASS);
    }

    public static ICodeNodeField createICodeNodeField()
    {
        return new ICodeNodeField(ICodeNodeTypeImpl.FIELD);
    }

    public static ICodeNodeEnumConstant createICodeNodeEnumConstant()
    {
        return new ICodeNodeEnumConstant(ICodeNodeTypeImpl.ENUM_CONSTANT);
    }

    public static ICodeNodeTypeId createICodeNodeTypeId()
    {
        return new ICodeNodeTypeId(ICodeNodeTypeImpl.TYPE);
    }

    public static ICodeNodeKeyValue createICodeNodeKeyValue()
    {
        return new ICodeNodeKeyValue(ICodeNodeTypeImpl.KEY_VALUE);
    }

    public static ICodeNodeNotationList createICodeNodeNotationList()
    {
        return new ICodeNodeNotationList(ICodeNodeTypeImpl.NOTATION_LIST);
    }

    public static ICodeNodeNotation createICodeNodeNotation()
    {
        return new ICodeNodeNotation(ICodeNodeTypeImpl.NOTATION);
    }

    public static ICodeNodeName createICodeNodeName()
    {
        return new ICodeNodeName(ICodeNodeTypeImpl.NAME);
    }

    public static ICodeNodeIdentifier createICodeNodeIdentifier()
    {
        return new ICodeNodeIdentifier(ICodeNodeTypeImpl.IDENTIFIER);
    }

    public static ICodeNodeList createICodeNodeList(ICodeNodeType type)
    {
        return new ICodeNodeList(type);
    }

    public static  <T extends ICodeNode> ITCodeNodeList<T> createITCodeNodeList(ICodeNodeType type)
    {
        return new ITCodeNodeList<>(type);
    }
    public static  <T extends ICodeNode> ITCodeNodeList<T> createITCodeNodeTemplateList()
    {
        return new ITCodeNodeList<>( ICodeNodeTypeImpl.TEMPLATE_LIST);
    }
    public static  <T extends ICodeNode> ITCodeNodeList<T> createITCodeNodeExtendList()
    {
        return new ITCodeNodeList<>( ICodeNodeTypeImpl.EXTEND_LIST);
    }
    public static  <T extends ICodeNode> ITCodeNodeList<T> createITCodeNodeImplementList()
    {
        return new ITCodeNodeList<>( ICodeNodeTypeImpl.IMPLEMENT_LIST);
    }
    public static <T extends ICodeNode> ITCodeNodeList<T> createICodeNodeModifierList()
    {
        return new ITCodeNodeList<>( ICodeNodeTypeImpl.MODIFIER_LIST);
    }
    public static  <T extends ICodeNodeImport> ITCodeNodeList<T> createITCodeNodeImportList()
    {
        return new ITCodeNodeList<>( ICodeNodeTypeImpl.IMPORT_LIST);
    }

    public static ICodeNodeList createICodeNodeFieldList()
    {
        return new ICodeNodeList(ICodeNodeTypeImpl.FIELD_LIST);
    }

    public static ICodeNodeTable createICodeNodeTable()
    {
        return new ICodeNodeTable(ICodeNodeTypeImpl.TABLE);
    }

    public static ICodeNodeQuery createICodeNodeQuery()
    {
        return new ICodeNodeQuery(ICodeNodeTypeImpl.QUERY);
    }

    public static ICodeNodeModifier createICodeNodeModifier(){return new ICodeNodeModifier(ICodeNodeTypeImpl.MODIFIER);}

    public static ICodeNodeStatement createICodeNodeStatement(){return new ICodeNodeStatement(ICodeNodeTypeImpl.STATEMENT);}

    public static ICodeNodeVarDeclarator createICodeNodeVarDeclarator(){return new ICodeNodeVarDeclarator(ICodeNodeTypeImpl.VAR_DECLARATOR);}

    public static ICodeNodeVarId createICodeNodeVarId(){return new ICodeNodeVarId(ICodeNodeTypeImpl.VAR_ID);}

    public static ICodeNodeImport createICodeNodeImport(){return new ICodeNodeImport(ICodeNodeTypeImpl.IMPORT);}

}
