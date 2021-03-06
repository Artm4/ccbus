/* Generated By:JJTree: Do not edit this line. SimpleNode.java Version 6.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package ccbus.tool.parser.react;
import ccbus.tool.intermediate.Token;

public
class SimpleNode extends ccbus.tool.intermediate.SimpleNode implements Node
{
  protected ReactParser parser;

  public SimpleNode(int i) {
    super(i);
  }

  public SimpleNode(ReactParser p, int i) {
    super(i);
    parser = p;
  }

  public Node createCopy(int id)
  {
    SimpleNode result=new SimpleNode(id);
    result.jjtSetFirstToken(jjtGetFirstToken());
    result.jjtSetLastToken(jjtGetLastToken());
    return result;
  }

  public Node createCopy()
  {
    Node result=new SimpleNode(this.getId());
    if(null!=jjtGetFirstToken())
    {
      result.jjtSetFirstToken(ccbus.tool.intermediate.Token.newToken(jjtGetFirstToken().kind, jjtGetFirstToken().image));
    }
    if(null!=jjtGetLastToken())
    {
      result.jjtSetLastToken(Token.newToken(jjtGetLastToken().kind, jjtGetLastToken().image));
    }
    return result;
  }
}

/* JavaCC - OriginalChecksum=2e5eaf6d975f1c6304320aa08b5a93a8 (do not edit this line) */
