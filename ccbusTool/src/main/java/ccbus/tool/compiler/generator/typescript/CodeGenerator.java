package ccbus.tool.compiler.generator.typescript;
import ccbus.tool.compiler.util.PrintStream;
import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.AngularParserTokenManager;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;

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
        ClassDeclaration classDeclaration=new ClassDeclaration(this);
        classDeclaration.generate(node);
    }

    protected boolean customGenerate(Node node)
    {
        switch (node.getId())
        {
            case AngularParserTreeConstants.JJTBLOCK:
                (new Block(this)).generate(node);
                return false;
            case AngularParserTreeConstants.JJTFORMALPARAMETER:
                (new FormalParameter(this)).generate(node);
                return false;
            case AngularParserTreeConstants.JJTBLOCKSTATEMENT: {
                (new BlockStatement(this)).generate(node);
                return false;
            }
            case AngularParserTreeConstants.JJTSTATEMENTEXPRESSION: {
                (new StatementExpression(this)).generate(node);
                return false;
            }
            case AngularParserTreeConstants.JJTSWITCHSTATEMENT: {
                (new SwitchStatement(this)).generate(node);
                return false;
            }
            case AngularParserTreeConstants.JJTSWITCHLABEL: {
                (new SwitchLabel(this)).generate(node);
                return false;
            }
            case AngularParserTreeConstants.JJTLOCALVARIABLEDECLARATION: {
                (new LocalVariableDeclaration(this)).generate(node);
                return false;
            }
            case AngularParserTreeConstants.JJTFORSTATEMENTRANGE: {
                (new ForStatementRange(this)).generate(node);
                return false;
            }
            case AngularParserTreeConstants.JJTFORSTATEMENT: {
                (new ForStatement(this)).generate(node);
                return false;
            }
            case AngularParserTreeConstants.JJTARRAYINITIALIZER: {
                (new ArrayInitializer(this)).generate(node);
                return false;
            }

        }
        return true;
    }

    public String kindToString(int kind)
    {
        return AngularParserTokenManager.jjstrLiteralImages[kind];
    }
}
