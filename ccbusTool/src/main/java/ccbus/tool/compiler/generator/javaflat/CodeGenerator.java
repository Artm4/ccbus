package ccbus.tool.compiler.generator.javaflat;

import ccbus.tool.compiler.util.PrintStream;
import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.java.JavaParserTokenManager;
import ccbus.tool.util.java.TokenList;

public class CodeGenerator extends ccbus.tool.compiler.generator.CodeGenerator
{
    public CodeGenerator()
    {
    }

    public CodeGenerator(CodeGenerator parent)
    {

    }

    public CodeGenerator(PrintStream ps)
    {
        printStream=ps;
    }

    public void generate(Node node)
    {
        process(node);
    }

    private void process(Node node)
    {
        CompilationUnit compilationUnit=new CompilationUnit(this);
        compilationUnit.generate(node);
    }

    protected boolean customGenerate(Node node)
    {
        switch (node.getId())
        {

        }
        return true;
    }

    public String kindToString(int kind)
    {
        return JavaParserTokenManager.jjstrLiteralImages[kind];
    }

    public void emitTokenList(Node node)
    {
        TokenList tokenList=new TokenList(node);
        tokenList.printWithSpecials(printStream);
    }

    protected void recursiveGenerate(Node node)
    {
        if(!customGenerate(node)){return;}

        for(int i=0;i<node.jjtGetNumChildren();i++)
        {
            recursiveGenerate(node.jjtGetChild(i));
        }
    }

}
