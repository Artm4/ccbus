package ccbus.dbtool.parser;

public interface Parser
{
    public Token currentToken();
    public Token getNextToken();
    public Token getToken(int index);
}
