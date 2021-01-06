package ccbus.tool.compiler.generator.ecmascript;
import ccbus.tool.compiler.util.PrintStream;
import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.EcmaParserTokenManager;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.parser.java.MyToken;
import ccbus.tool.util.java.Functor;
import ccbus.tool.util.react.TokenList;

import java.util.ArrayList;

public class CodeGenerator extends ccbus.tool.compiler.generator.CodeGenerator
{
    public ArrayList<Functor<CodeGenerator> > preClassDeclaration=new ArrayList<>();
    public ArrayList<Functor<CodeGenerator> > postActionMethodDeclaration=new ArrayList<>();
    public ArrayList<Functor<CodeGenerator> > postActionFunctionDeclaration=new ArrayList<>();

    public CodeGenerator()
    {
    }

    public CodeGenerator(CodeGenerator parent)
    {
        preClassDeclaration=parent.preClassDeclaration;
        postActionMethodDeclaration=parent.postActionMethodDeclaration;
        postActionFunctionDeclaration=parent.postActionFunctionDeclaration;
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
            case EcmaParserTreeConstants.JJTBLOCK:
                (new Block(this)).generate(node);
                return false;
            case EcmaParserTreeConstants.JJTFORMALPARAMETER:
                (new FormalParameter(this)).generate(node);
                return false;
            case EcmaParserTreeConstants.JJTBLOCKSTATEMENT: {
                (new BlockStatement(this)).generate(node);
                return false;
            }
            case EcmaParserTreeConstants.JJTSTATEMENTEXPRESSION: {
                (new StatementExpression(this)).generate(node);
                return false;
            }
            case EcmaParserTreeConstants.JJTSWITCHSTATEMENT: {
                (new SwitchStatement(this)).generate(node);
                return false;
            }
            case EcmaParserTreeConstants.JJTSWITCHLABEL: {
                (new SwitchLabel(this)).generate(node);
                return false;
            }
            case EcmaParserTreeConstants.JJTLOCALVARIABLEDECLARATION: {
                (new LocalVariableDeclaration(this)).generate(node);
                return false;
            }
            case EcmaParserTreeConstants.JJTFORSTATEMENTRANGE: {
                (new ForStatementRange(this)).generate(node);
                return false;
            }
            case EcmaParserTreeConstants.JJTFORSTATEMENT: {
                (new ForStatement(this)).generate(node);
                return false;
            }
            case EcmaParserTreeConstants.JJTARRAYINITIALIZER: {
                (new ArrayInitializer(this)).generate(node);
                return false;
            }
            case EcmaParserTreeConstants.JJTLITERALDECLARATION: {
                (new LiteralDeclaration(this)).generate(node);
                return false;
            }
            case EcmaParserTreeConstants.JJTALLOCATIONEXPRESSION: {
                (new AllocationExpression(this)).generate(node);
                return false;
            }
            case EcmaParserTreeConstants.JJTLAMDAEXPRESSION: {
                (new LamdaExpression(this)).generate(node);
                return false;
            }
            case EcmaParserTreeConstants.JJTRETURNSTATEMENT: {
                (new ReturnStatement(this)).generate(node);
                return false;
            }
            case EcmaParserTreeConstants.JJTTYPEARGUMENTS: {
                // do nothing
                return false;
            }
        }
        return true;
    }

    public String kindToString(int kind)
    {
        return EcmaParserTokenManager.jjstrLiteralImages[kind];
    }

    public void emitTokenList(TokenList tokenList)
    {
        tokenList.printWithSpecials(printStream);
    }

    public void emitTokenList(Node node)
    {
        if( node.jjtGetFirstToken() instanceof MyToken)
        {
            ccbus.tool.util.java.TokenList tokenList=new ccbus.tool.util.java.TokenList(node);
            tokenList.printWithSpecials(printStream);
            return;
        }

        TokenList tokenList=new TokenList(node);
        tokenList.printWithSpecials(printStream);
    }

    public void firePostMethodDeclActions()
    {
        for(Functor n : postActionMethodDeclaration)
        {
            n.apply();
        }
        postActionMethodDeclaration=new ArrayList<>();
    }

    public void firePostFunctionDeclActions()
    {
        for(Functor n : postActionFunctionDeclaration)
        {
            n.apply();
        }
        postActionFunctionDeclaration=new ArrayList<>();
    }

    public void firePreClassDeclaration()
    {
        for(Functor n : preClassDeclaration)
        {
            n.apply();
        }
        preClassDeclaration=new ArrayList<>();
    }
}
