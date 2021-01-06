package ccbus.tool.parser;

import java.util.HashSet;

public class Util<Token>
{
    private final IParser<Token> entity;
    private final int EOF;
    public Util(IParser entity,int eof)
    {
        this.entity=entity;
        EOF=eof;
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
                && !equalsTokenType(lookaheadToken,EOF))
        {
            token=nextToken();
            lookaheadToken=lookaheadToken();
        }
        return token;
    }

    /**
     *
     * @param syncEntityConstant
     * @return currentToken that points before first encountered sync token
     */
    public Token synchronizeSeq(Integer ...syncEntityConstant)
    {
        if(0==syncEntityConstant.length)
        {
            return null;
        }

        Integer firstTokenKind=syncEntityConstant[0];

        synchronize(firstTokenKind);
        Token firstSyncToken=lookaheadToken();
        if(equalsTokenType(firstSyncToken,firstTokenKind))
        {
            int j;
            for(j=1;
                    j<syncEntityConstant.length && equalsTokenType(lookaheadToken(j),syncEntityConstant[j-1]);
                    j++)
            {}
            if(j==syncEntityConstant.length)
            {
                return firstSyncToken;
            }
            else
            {
                for(j=1;
                    j<syncEntityConstant.length;
                    j++)
                {
                    nextToken();
                }
            }

            return synchronizeSeq(syncEntityConstant);
        }

        Token token=currentToken();
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
                && !equalsTokenType(lookaheadToken,EOF))
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
                && !equalsTokenType(lookaheadToken,EOF))
        {
            token=nextToken(); // consume next token
            result=result.concat(getTokenImage(token));
            if(equalsTokenType(token,openEntityConstant))
            {
                consumeBetweenOpenClose(openEntityConstant,closeEntityConstant);
                token=nextToken(); // consume close token
                result=result.concat(getTokenImage(token));
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

    public Token lookaheadToken(int i)
    {
        return this.entity.getToken(i);
    }

    public Token nextToken()
    {
        return this.entity.getNextToken();
    }

    public int getTokenType(Token token)
    {
        return this.entity.getTokenKind(token);
    }

    public String getTokenImage(Token token)
    {
        return this.entity.getTokenImage(token);
    }

    public boolean isEndOfFile(Token token)
    {
        return getTokenType(token)==EOF;
    }

    public boolean equalsTokenType(Token token,int tokenType)
    {
        return getTokenType(token)==tokenType;
    }
}