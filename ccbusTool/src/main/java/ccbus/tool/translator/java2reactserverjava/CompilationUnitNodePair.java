package ccbus.tool.translator.java2reactserverjava;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.java.ASTCompilationUnit;
import ccbus.tool.parser.java.JavaParserTreeConstants;

import java.util.ArrayList;

public class CompilationUnitNodePair
{
    private Node clientCompilationUnit;
    private ArrayList<Node> serverCompilationUnitList=new ArrayList<>();

    public CompilationUnitNodePair()
    {

    }

    public ASTCompilationUnit createServerCompilationUnit()
    {
        ASTCompilationUnit compilationUnitServer=new ASTCompilationUnit(JavaParserTreeConstants.JJTCOMPILATIONUNIT);
        this.serverCompilationUnitList.add(compilationUnitServer);
        return compilationUnitServer;
    }

    public ArrayList<Node> getServerCompilationUnitList()
    {
        return serverCompilationUnitList;
    }

    public Node getClientCompilationUnit()
    {
        return clientCompilationUnit;
    }

    public void setClientCompilationUnit(Node clientCompilationUnit)
    {
        this.clientCompilationUnit = clientCompilationUnit;
    }

    public void addToAllServer(Node n)
    {
        for(Node node:serverCompilationUnitList)
        {
            node.add(n);
        }
    }
}
