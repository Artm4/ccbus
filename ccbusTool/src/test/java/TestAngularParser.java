import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.AngularParser;
import ccbus.tool.parser.typescript.ParseException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestAngularParser
{
    static AngularParser parser;
    @BeforeClass
    public static void setUp()
    {
        parser=new AngularParser("src/main/resources/source/react.component.js");
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
    public void testParsingDecl()
    {
        Node root=parser.getTree().rootNode();
        root.getId();
    }
}
