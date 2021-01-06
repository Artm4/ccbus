package ccbus.tool.translator.java2reactserverjava;

import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.TreeTranslator;

import java.util.HashMap;

public class TranslatorTable extends HashMap<Integer,TreeTranslator> {
    static final int SINGLE_TOKEN=-1;
    static final int RECURSIVE_TRANSLATOR=-2;
    public static TranslatorTable table=new TranslatorTable();
    static
    {
        table.put(JavaParserTreeConstants.JJTCLASSORINTERFACEBODY,new ClassOrInterfaceBody());
        table.put(JavaParserTreeConstants.JJTPRIMARYEXPRESSION,new PrimaryExpression());
        table.put(JavaParserTreeConstants.JJTALLOCATIONEXPRESSION,new AllocationExpression());
        table.put(JavaParserTreeConstants.JJTIMPORTDECLARATION,new ImportDeclaration());
        table.put(JavaParserTreeConstants.JJTPACKAGEDECLARATION,new PackageDeclaration());
        table.put(JavaParserTreeConstants.JJTMETHODDECLARATION,new MethodDeclaration());
        table.put(JavaParserTreeConstants.JJTCONSTRUCTORDECLARATION,new ConstructorDeclaration());
        table.put(JavaParserTreeConstants.JJTLAMDAEXPRESSION,new LamdaExpression());

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
