import ccbus.tool.compiler.generator.typescript.CodeGenerator;
import ccbus.tool.compiler.util.PrintStream;
import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.ASTCompilationUnit;
import ccbus.tool.parser.java.JavaParser;
import ccbus.tool.parser.java.ParseException;
import ccbus.tool.translator.java2typescript.TranslatedTree;
import ccbus.tool.translator.java2typescript.CompilationUnit;
import ccbus.tool.util.java.Tool;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class TestClassDecl
{
    static JavaParser parser;
    @BeforeClass
    public static void setUp() throws IOException, Exception
    {
        Tool tool=new Tool("src/main/resources/source/configCcbus.ini");
        parser=new JavaParser("src/main/resources/source/AngularComponent.java",tool);
    }

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
    public void testGenDecl() throws IOException {

        Node root=parser.getTree().rootNode();
        CompilationUnit cu=new CompilationUnit();
        ASTCompilationUnit compilationUnit=(ASTCompilationUnit)cu.translate(root,new TranslatedTree(root));
        CodeGenerator codeGenerator=new CodeGenerator(new PrintStream(System.out));
        codeGenerator.generate(compilationUnit);
    }
}
