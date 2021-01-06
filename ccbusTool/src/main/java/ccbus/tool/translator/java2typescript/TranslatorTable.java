package ccbus.tool.translator.java2typescript;

import java.util.HashMap;

import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.TreeTranslator;

public class TranslatorTable extends HashMap<Integer,TreeTranslator> {
    static final int SINGLE_TOKEN=-1;
    static final int RECURSIVE_TRANSLATOR=-2;
    public static TranslatorTable table=new TranslatorTable();
    static
    {
        table.put(JavaParserTreeConstants.JJTCOMPILATIONUNIT,new CompilationUnit());
        table.put(JavaParserTreeConstants.JJTTYPEDECLARATION,new TypeDeclaration());
        table.put(JavaParserTreeConstants.JJTCLASSORINTERFACEBODY,new ClassBody());
        table.put(JavaParserTreeConstants.JJTCLASSORINTERFACEDECLARATION,new ClassDeclaration());
        table.put(JavaParserTreeConstants.JJTMODIFIERS,new Modifiers());
        table.put(JavaParserTreeConstants.JJTIDENTIFIER,new Identifier());
        table.put(JavaParserTreeConstants.JJTTYPEPARAMETERS,new TypeParameters());
        table.put(JavaParserTreeConstants.JJTFIELDDECLARATION,new FieldDeclaration());
        table.put(JavaParserTreeConstants.JJTMETHODDECLARATION,new MethodDeclaration());
        table.put(JavaParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION,new ClassBodyDeclaration());
        table.put(JavaParserTreeConstants.JJTTYPE,new Type());
        table.put(JavaParserTreeConstants.JJTPRIMITIVETYPE,new PrimitiveType());
        table.put(JavaParserTreeConstants.JJTREFERENCETYPE,new ReferenceType());
        table.put(JavaParserTreeConstants.JJTCLASSORINTERFACETYPE,new ClassOrInterfaceType());
        table.put(JavaParserTreeConstants.JJTVARIABLEDECLARATOR,new VariableDeclarator());
        table.put(JavaParserTreeConstants.JJTBRACKET,new Bracket());
        table.put(JavaParserTreeConstants.JJTVARIABLEDECLARATORID,new VariableDeclaratorId());
        table.put(JavaParserTreeConstants.JJTTYPEARGUMENTS,new TypeArguments());
        table.put(JavaParserTreeConstants.JJTMETHODDECLARATOR,new MethodDeclarator());
        table.put(JavaParserTreeConstants.JJTRESULTTYPE,new ResultType());
        table.put(JavaParserTreeConstants.JJTFORMALPARAMETERS,new FormalParameters());
        table.put(JavaParserTreeConstants.JJTFORMALPARAMETER,new FormalParameter());
        table.put(JavaParserTreeConstants.JJTBLOCKSTATEMENT,new BlockStatement());
        table.put(JavaParserTreeConstants.JJTBLOCK,new Block());
        table.put(JavaParserTreeConstants.JJTSTATEMENT,new Statement());
        table.put(JavaParserTreeConstants.JJTLOCALVARIABLEDECLARATION,new LocalVariableDeclaration());
        table.put(JavaParserTreeConstants.JJTSTATEMENTEXPRESSION,new StatementExpression());
        table.put(JavaParserTreeConstants.JJTEXPRESSION,new Expression());
        table.put(JavaParserTreeConstants.JJTFORSTATEMENTRANGE,new ForStatementRange());

        table.put(SINGLE_TOKEN,new SingleToken());
        table.put(RECURSIVE_TRANSLATOR,new RecursiveTranslator());

    }

    public static TreeTranslator getTranslator(Integer id)
    {
        return table.get(id);
    }

    public static TreeTranslator getTranslator(int id)
    {
        return table.get(id);
    }

    public static SingleToken getSingleToken()
    {
        return (SingleToken)table.get(SINGLE_TOKEN);
    }

    public static RecursiveTranslator getRecursiveTranslator()
    {
        return (RecursiveTranslator)table.get(RECURSIVE_TRANSLATOR);
    }
}
