package ccbus.tool.util.react;

import ccbus.tool.compiler.util.PrintStream;
import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.react.Token;

public class TokenList
{
    Token head;
    Token tail;

    public TokenList(Token head,Token tail)
    {
        this.head=head;
        this.tail=tail;
    }

    public TokenList(Node root)
    {
        this.head=(Token)root.jjtGetFirstToken();
        this.tail=(Token)root.jjtGetLastToken();
    }

    public TokenList()
    {
        this(null,null);
    }

    private void printSpecialTokens(java.io.PrintStream ps, Token st )
    {
        if( st != null )
        {
            printSpecialTokens( ps, st.specialToken ) ;
            ps.print( st.image ) ;
        }
    }

    public void printWithSpecials( java.io.PrintStream ps )
    {
        if(null==head)
        {
            return;
        }
        for( Token p = head ; p != tail ; p = p.next )
        {
            printSpecialTokens( ps, p.specialToken ) ;
            ps.print( p.image ) ;
        }
        printSpecialTokens(ps, tail) ;
    }

    private void printSpecialTokens(PrintStream ps, Token st )
    {
        if( st != null )
        {
            printSpecialTokens( ps, st.specialToken ) ;
            ps.emit( st.image );
        }
    }

    public void printWithSpecials( PrintStream ps )
    {
        if(null==head)
        {
            return;
        }
        for( Token p = head ; p != tail ; p = p.next )
        {
            printSpecialTokens( ps, p.specialToken ) ;
            ps.emit( p.image ) ;
        }
        printSpecialTokens(ps, tail) ;
    }
}