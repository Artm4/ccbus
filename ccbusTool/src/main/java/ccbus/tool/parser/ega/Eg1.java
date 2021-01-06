/* Eg1.java */
/* Generated By:JJTree&JavaCC: Do not edit this line. Eg1.java */
package ccbus.tool.parser.ega;

/** An Arithmetic Grammar. */
public class Eg1/*@bgen(jjtree)*/implements Eg1TreeConstants, Eg1Constants {/*@bgen(jjtree)*/
  protected JJTEg1State jjtree = new JJTEg1State();
  /** Main entry point. */
  public static void main(String args[]) {
    System.out.println("Reading from standard input...");

    Eg1 t=null;

    if (args.length == 0) {
          System.out.println("Java Parser Version 1.1:  Reading from standard input . . .");
          t = new Eg1(System.in);
        } else if (args.length == 1) {
          System.out.println("Java Parser Version 1.1:  Reading from file " + args[0] + " . . .");
          try {
            t = new Eg1(new java.io.FileInputStream(args[0]));
          } catch (java.io.FileNotFoundException e) {
            System.out.println("Java Parser Version 1.1:  File " + args[0] + " not found.");
            return;
          }
        }


    try {
      SimpleNode n = t.Start();
      n.dump("");
      System.out.println("Thank you.");
    } catch (Exception e) {
      System.out.println("Oops.");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

/** Main production. */
  final public SimpleNode Start() throws ParseException {/*@bgen(jjtree) Start */
  ASTStart jjtn000 = new ASTStart(JJTSTART);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
  jjtn000.jjtSetFirstToken(getToken(1));
    try {
      Expression();
      jj_consume_token(14);
jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
    jjtn000.jjtSetLastToken(getToken(0));
{if ("" != null) return jjtn000;}
    } catch (Throwable jjte000) {
if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
      jjtn000.jjtSetLastToken(getToken(0));
    }
    }
    throw new Error("Missing return statement in function");
  }

/** An Expression. */
  final public void Expression() throws ParseException {/*@bgen(jjtree) Expression */
  ASTExpression jjtn000 = new ASTExpression(JJTEXPRESSION);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
  jjtn000.jjtSetFirstToken(getToken(1));
    try {
      AdditiveExpression();
    } catch (Throwable jjte000) {
if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
      jjtn000.jjtSetLastToken(getToken(0));
    }
    }
  }

/** An Additive Expression. */
  final public void AdditiveExpression() throws ParseException {/*@bgen(jjtree) AdditiveExpression */
  ASTAdditiveExpression jjtn000 = new ASTAdditiveExpression(JJTADDITIVEEXPRESSION);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
  jjtn000.jjtSetFirstToken(getToken(1));
    try {
      MultiplicativeExpression();
      label_1:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 15:
        case 16:{
          ;
          break;
          }
        default:
          break label_1;
        }
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 15:{
          jj_consume_token(15);
          break;
          }
        case 16:{
          jj_consume_token(16);
          break;
          }
        default:
          jj_consume_token(-1);
          throw new ParseException();
        }
        MultiplicativeExpression();
      }
    } catch (Throwable jjte000) {
if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
      jjtn000.jjtSetLastToken(getToken(0));
    }
    }
  }

/** A Multiplicative Expression. */
  final public void MultiplicativeExpression() throws ParseException {/*@bgen(jjtree) MultiplicativeExpression */
  ASTMultiplicativeExpression jjtn000 = new ASTMultiplicativeExpression(JJTMULTIPLICATIVEEXPRESSION);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
  jjtn000.jjtSetFirstToken(getToken(1));
    try {
      UnaryExpression();
      label_2:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 17:
        case 18:
        case 19:{
          ;
          break;
          }
        default:
          break label_2;
        }
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 17:{
          jj_consume_token(17);
          break;
          }
        case 18:{
          jj_consume_token(18);
          break;
          }
        case 19:{
          jj_consume_token(19);
          break;
          }
        default:
          jj_consume_token(-1);
          throw new ParseException();
        }
        UnaryExpression();
      }
    } catch (Throwable jjte000) {
if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
      jjtn000.jjtSetLastToken(getToken(0));
    }
    }
  }

/** A Unary Expression. */
  final public void UnaryExpression() throws ParseException {/*@bgen(jjtree) UnaryExpression */
  ASTUnaryExpression jjtn000 = new ASTUnaryExpression(JJTUNARYEXPRESSION);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
  jjtn000.jjtSetFirstToken(getToken(1));
    try {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 20:{
        jj_consume_token(20);
        Expression();
        jj_consume_token(21);
        break;
        }
      case IDENTIFIER:{
        Identifier();
        break;
        }
      case INTEGER_LITERAL:{
        Integer();
        break;
        }
      default:
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (Throwable jjte000) {
if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
      jjtn000.jjtSetLastToken(getToken(0));
    }
    }
  }

/** An Identifier. */
  final public void Identifier() throws ParseException {/*@bgen(jjtree) Identifier */
  ASTIdentifier jjtn000 = new ASTIdentifier(JJTIDENTIFIER);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
  jjtn000.jjtSetFirstToken(getToken(1));
    try {
      jj_consume_token(IDENTIFIER);
    } finally {
if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
      jjtn000.jjtSetLastToken(getToken(0));
    }
    }
  }

/** An Integer. */
  final public void Integer() throws ParseException {/*@bgen(jjtree) Integer */
  ASTInteger jjtn000 = new ASTInteger(JJTINTEGER);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
  jjtn000.jjtSetFirstToken(getToken(1));
    try {
      jj_consume_token(INTEGER_LITERAL);
    } finally {
if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
      jjtn000.jjtSetLastToken(getToken(0));
    }
    }
  }

  /** Generated Token Manager. */
  public Eg1TokenManager token_source;
  JavaCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;

  /** Constructor with InputStream. */
  public Eg1(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Eg1(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new JavaCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new Eg1TokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
  }

  /** Constructor. */
  public Eg1(java.io.Reader stream) {
    jj_input_stream = new JavaCharStream(stream, 1, 1);
    token_source = new Eg1TokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
  }

  /** Constructor with generated Token Manager. */
  public Eg1(Eg1TokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
  }

  /** Reinitialise. */
  public void ReInit(Eg1TokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      return token;
    }
    token = oldToken;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk_f() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
    Token errortok = token.next;
    int line = errortok.beginLine, column = errortok.beginColumn;
    String mess = (errortok.kind == 0) ? tokenImage[0] : errortok.image;
    return new ParseException("Parse error at line " + line + ", column " + column + ".  Encountered: " + mess);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
