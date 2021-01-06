package ccbus.tool.parser;

public interface IParser<Token>
{
    public Token currentToken();

    public Token getToken(int index);

    public Token getNextToken();

    public int getTokenKind(Object token);

    public String getTokenImage(Object token);

}
