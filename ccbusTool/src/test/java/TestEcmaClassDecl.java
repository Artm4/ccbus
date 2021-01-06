import ccbus.tool.compiler.Compiler;
import ccbus.tool.compiler.JavaToReact;
import ccbus.tool.compiler.generator.ecmascript.CodeGenerator;
import ccbus.tool.compiler.util.PrintStream;
import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.ASTCompilationUnit;
import ccbus.tool.parser.java.JavaParser;
import ccbus.tool.parser.java.ParseException;
import ccbus.tool.translator.java2ecmascript.TranslatedTree;
import ccbus.tool.translator.java2ecmascript.CompilationUnit;
import ccbus.tool.translator.java2reactserverjava.CompilationUnitNodePair;
import ccbus.tool.util.java.TokenList;
import ccbus.tool.util.java.Tool;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class TestEcmaClassDecl
{
    @BeforeClass
    public static void setUp()throws IOException,Exception
    {
        tool=new Tool(configPath);

        parser=new JavaParser(compilationUnitPath,tool);

    }


    static JavaParser parser;
    static Tool tool;
    static String configPath="/home/artyom/dev/java/ccbusTool/target/configCcbus.ini";
    static String compilationUnitPath=
            "/home/artyom/dev/java/ccbusUIWeb/src/main/java/ccbus/ui/web/ccbus/web/components/list/PagerMy.java";



    @Before
    public void setUpTest()
    {
        try
        {
            parser.CompilationUnit();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testParsingDecl()
    {
        Node root=parser.getTree().rootNode();
        CompilationUnit cu=new CompilationUnit();
        cu.translate(root,new TranslatedTree(root));
    }

    @Test
    public void testGenDecl() throws IOException {

        Node root=parser.getTree().rootNode();
        CompilationUnit cu=new CompilationUnit();
        ASTCompilationUnit compilationUnit=(ASTCompilationUnit)cu.translate(root,new TranslatedTree(root,tool));
        CodeGenerator codeGenerator=new CodeGenerator(new PrintStream(System.out));
        codeGenerator.generate(compilationUnit);
    }

    @Test
    public void testTranslateJavaReact() throws IOException {

        Node root=parser.getTree().rootNode();
        ccbus.tool.translator.java2reactserverjava.CompilationUnit cu=
                new ccbus.tool.translator.java2reactserverjava.CompilationUnit();
        CompilationUnitNodePair compilationUnitPair=
                cu.translateClientServer(root,new ccbus.tool.translator.java2reactserverjava.TranslatedTree(root,tool));

        CompilationUnit cuEcma=new CompilationUnit();
        ASTCompilationUnit compilationUnitEcma=(ASTCompilationUnit)cuEcma.translate(compilationUnitPair.getClientCompilationUnit(),new TranslatedTree(root,tool));


        System.out.println("CLIENT CLASS:");
        CodeGenerator codeGenerator=new CodeGenerator(new PrintStream(System.out));
        codeGenerator.generate(compilationUnitEcma);


        int i=0;
        for(Node n:compilationUnitPair.getServerCompilationUnitList())
        {
            System.out.println();
            System.out.println("SERVER CLASS:"+i);
            ccbus.tool.compiler.generator.java.CodeGenerator codeGeneratorServer=
                    new ccbus.tool.compiler.generator.java.CodeGenerator(new PrintStream(System.out));
            codeGeneratorServer.generate(n);
            i++;
            System.out.println();
        }
    }


    @Test
    public void testTranslateJava() throws IOException {

        Node root=parser.getTree().rootNode();
        TokenList tokenList=new TokenList(root);
        tokenList.printWithSpecials(System.out);
        //System.out.print(root.toString());
    }

    @Test
    public void testCompiler() throws IOException,Exception
    {
        Compiler javaToReact=new JavaToReact(configPath,compilationUnitPath);
        javaToReact.compile();
    }

}
