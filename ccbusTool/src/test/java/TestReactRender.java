import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.react.ReactParser;

import ccbus.tool.parser.react.ParseException;
import ccbus.tool.util.react.TokenList;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestReactRender
{
    static ReactParser parser;

    @BeforeClass
    public static void setUp()
    {
        parser=new ReactParser("src/main/resources/source/react.component.js");
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
        Node root= parser.getTree().rootNode();
        root.getId();
        TokenList tokenList=parser.getRenderBodyList().get(0);
        tokenList.printWithSpecials(System.out);
    }
}
