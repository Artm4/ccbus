package ccbus.dbtool.frontend;

import java.util.HashSet;

import ccbus.dbtool.parser.*;

public class Util{
    private final Parser entity;
    public Util(Parser entity)
    {
        this.entity=entity;
    }

    /**
     *
     * @param syncEntityConstant
     * @return currentToken that points before first encountered sync token
     */
    public Token synchronize(Integer ...syncEntityConstant)
    {
        HashSet<Integer> syncSet = new HashSet<Integer>();

        for (Integer s : syncEntityConstant) {
            syncSet.add(s);
        }

        Token token=currentToken();
        Token lookaheadToken=lookaheadToken();
        while(!syncSet.contains(getTokenType(lookaheadToken))
                && !equalsTokenType(lookaheadToken,EntityConstants.EOF))
        {
            token=nextToken();
            lookaheadToken=lookaheadToken();
        }
        return token;
    }

    /**
     *
     * @param openEntityConstant
     * @param closeEntityConstant
     * @return currentToken that points before close token
     */
    public Token consumeBetweenOpenClose(int openEntityConstant,int closeEntityConstant)
    {
        Token token=currentToken();
        Token lookaheadToken=lookaheadToken();
        if(!equalsTokenType(token,openEntityConstant))
        {
            return token;
        }
        while(!equalsTokenType(lookaheadToken,closeEntityConstant)
                && !equalsTokenType(lookaheadToken,EntityConstants.EOF))
        {
            token=nextToken(); // consume next token
            if(equalsTokenType(token,openEntityConstant))
            {
                consumeBetweenOpenClose(openEntityConstant,closeEntityConstant);
                token=nextToken(); // consume close token
            }
            lookaheadToken=lookaheadToken();
        }
        return token;
    }
    /**
     *
     * @param openEntityConstant
     * @param closeEntityConstant
     * @return currentToken that points before close token
     */
    public String consumeBetweenOpenCloseAsString(int openEntityConstant, int closeEntityConstant, String result)
    {
        Token token=currentToken();
        Token lookaheadToken=lookaheadToken();
        if(!equalsTokenType(token,openEntityConstant))
        {
            return result;
        }
        while(!equalsTokenType(lookaheadToken,closeEntityConstant)
                && !equalsTokenType(lookaheadToken,EntityConstants.EOF))
        {
            token=nextToken(); // consume next token
            result=result.concat(token.image);
            if(equalsTokenType(token,openEntityConstant))
            {
                consumeBetweenOpenClose(openEntityConstant,closeEntityConstant);
                token=nextToken(); // consume close token
                result=result.concat(token.image);
            }
            lookaheadToken=lookaheadToken();
        }
        return result;
    }


    public Token currentToken()
    {
        return this.entity.currentToken();
    }

    public Token lookaheadToken()
    {
        return this.entity.getToken(1);
    }

    public Token nextToken()
    {
        return this.entity.getNextToken();
    }

    public int getTokenType(Token token)
    {
        return token.kind;
    }

    public boolean isEndOfFile(Token token)
    {
        return getTokenType(token)==EntityConstants.EOF;
    }

    public boolean equalsTokenType(Token token,int tokenType)
    {
        return getTokenType(token)==tokenType;
    }
}