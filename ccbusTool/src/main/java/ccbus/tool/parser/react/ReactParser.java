/* ReactParser.java */
/* Generated By:JJTree&JavaCC: Do not edit this line. ReactParser.java */
package ccbus.tool.parser.react;
import java.io.*;
import ccbus.tool.parser.IParser;
import ccbus.tool.parser.Util;
import ccbus.tool.util.react.TokenList;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Grammar to parse Java version 1.5
 * @author Sreenivasa Viswanadha - Simplified and enhanced for 1.5
 */
public class ReactParser implements/*@bgen(jjtree)*/ ReactParserTreeConstants,IParser, ReactParserConstants {/*@bgen(jjtree)*/
  protected JJTReactParserState jjtree = new JJTReactParserState();private Util<Token> util;
    ArrayList<TokenList> importList=new ArrayList();
    ArrayList<TokenList> renderBodyList=new ArrayList();
    ArrayList<TokenList> renderFunctionBodyList=new ArrayList();
    HashMap<String,String> importedNames=new HashMap();

    public void init()
    {
     util=new Util<Token>(this,ReactParserConstants.EOF);
    }

    public int getTokenKind(Object token)
        {
            return ((Token)token).kind;
        }

        public String getTokenImage(Object token)
        {
            return ((Token)token).image;
        }

        public ArrayList<TokenList> getRenderBodyList()
        {
            return renderBodyList;
        }

         public ArrayList<TokenList> getRenderFunctionBodyList()
            {
                return renderFunctionBodyList;
            }

            public HashMap<String, String> getImportedNames() {
                    return importedNames;
                }

        public ArrayList<TokenList> getImportList()
        {
            return importList;
        }

    public Token currentToken()
      {
            return this.token;
      }

    public JJTReactParserState getTree()
    {
        return jjtree;
    }

   public ReactParser(String fileName)
   {
      this(System.in);
      try { ReInit(new FileInputStream(new File(fileName))); }
      catch(Exception e) { e.printStackTrace(); }
   }

   public void parseRender()
   {
        do
        {
            util.synchronizeSeq(ReactParserConstants.RENDER,
                    ReactParserConstants.LPAREN);

            if(util.getTokenType(util.lookaheadToken())==ReactParserConstants.RENDER &&
                    util.getTokenType(util.lookaheadToken(2))==ReactParserConstants.LPAREN)
            {
                boolean functionDefinition=false;
                if(util.getTokenType(util.currentToken())==ReactParserConstants.FUNCTION)
                {
                    functionDefinition=true;
                }
                Token head = util.lookaheadToken();
                if(util.currentToken().image.compareTo("static")==0)
                {
                    head=util.currentToken();
                }
                util.synchronizeSeq(
                        ReactParserConstants.RPAREN,
                        ReactParserConstants.LBRACE);

                if (
                        util.getTokenType(util.lookaheadToken()) == ReactParserConstants.RPAREN &&
                                util.getTokenType(util.lookaheadToken(2)) == ReactParserConstants.LBRACE

                        )
                {
                    util.nextToken(); //consume RPAREN
                    util.nextToken(); //consume LBRACE set currentToken to LBRACE
                    util.consumeBetweenOpenClose(
                            ReactParserConstants.LBRACE,
                            ReactParserConstants.RBRACE);

                    Token tail = util.lookaheadToken();
                    TokenList renderBody = new TokenList(head, tail);
                    if(functionDefinition)
                    {
                        renderFunctionBodyList.add(renderBody);
                    }
                    else
                    {
                        renderBodyList.add(renderBody);
                    }
                    functionDefinition=false;

                } else
                {
                    util.nextToken();
                }


            }
            else
            {
                util.nextToken();
            }
        }
        while(util.currentToken().kind!=ReactParserConstants.EOF);
   }

  public static void main(String args[]) {
    ReactParser parser;
    if (args.length == 0) {
      System.out.println("Java Parser Version 1.1:  Reading from standard input . . .");
      parser = new ReactParser(System.in);
    } else if (args.length == 1) {
      System.out.println("Java Parser Version 1.1:  Reading from file " + args[0] + " . . .");
      try {
        parser = new ReactParser(new java.io.FileInputStream(args[0]));
      } catch (java.io.FileNotFoundException e) {
        System.out.println("Java Parser Version 1.1:  File " + args[0] + " not found.");
        return;
      }
    } else {
      System.out.println("Java Parser Version 1.1:  Usage is one of:");
      System.out.println("         java ReactParser < inputfile");
      System.out.println("OR");
      System.out.println("         java ReactParser inputfile");
      return;
    }
    try {
      parser.CompilationUnit();
      System.out.println("Java Parser Version 1.1:  Java program parsed successfully.");
    } catch (ParseException e) {
      System.out.println(e.getMessage());
      System.out.println("Java Parser Version 1.1:  Encountered errors during parse.");
    }
  }

/*****************************************
 * REACT RENDER GRAMMAR STARTS HERE *
 *****************************************/

/*
 * Program structuring syntax follows.
 */
  final public 

void ImportDeclaration() throws ParseException {/*@bgen(jjtree) ImportDeclaration */
 ASTImportDeclaration jjtn000 = new ASTImportDeclaration(JJTIMPORTDECLARATION);
 boolean jjtc000 = true;
 jjtree.openNodeScope(jjtn000);
 jjtn000.jjtSetFirstToken(getToken(1));Token head;
Token tail;
    try {
      jj_consume_token(IMPORT);
head=this.currentToken();
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case LBRACE:{
        jj_consume_token(LBRACE);
        break;
        }
      default:
        ;
      }
      ImportName();
      label_1:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case COMMA:{
          ;
          break;
          }
        default:
          break label_1;
        }
        jj_consume_token(COMMA);
        ImportName();
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case RBRACE:{
        jj_consume_token(RBRACE);
        break;
        }
      default:
        ;
      }
      jj_consume_token(FROM);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case CHARACTER_LITERAL:{
        jj_consume_token(CHARACTER_LITERAL);
        break;
        }
      case STRING_LITERAL:{
        jj_consume_token(STRING_LITERAL);
        break;
        }
      default:
        jj_consume_token(-1);
        throw new ParseException();
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case SEMICOLON:{
        jj_consume_token(SEMICOLON);
        break;
        }
      default:
        ;
      }
jjtree.closeNodeScope(jjtn000, true);
     jjtc000 = false;
     jjtn000.jjtSetLastToken(getToken(0));
tail=this.currentToken();
    TokenList importTokenList = new TokenList(head, tail);
    importList.add(importTokenList);
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

  final public void ImportName() throws ParseException {/*@bgen(jjtree) ImportName */
 ASTImportName jjtn000 = new ASTImportName(JJTIMPORTNAME);
 boolean jjtc000 = true;
 jjtree.openNodeScope(jjtn000);
 jjtn000.jjtSetFirstToken(getToken(1));String importName;
    try {
      Name();
importName=this.currentToken().image;
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case AS:{
        jj_consume_token(AS);
        Name();
importName=this.currentToken().image;
        break;
        }
      default:
        ;
      }
jjtree.closeNodeScope(jjtn000, true);
       jjtc000 = false;
       jjtn000.jjtSetLastToken(getToken(0));
importedNames.put(importName,importName);
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

  final public void Name() throws ParseException {/*@bgen(jjtree) Name */
  ASTName jjtn000 = new ASTName(JJTNAME);
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

  final public void CompilationUnit() throws ParseException {/*@bgen(jjtree) CompilationUnit */
    ASTCompilationUnit jjtn000 = new ASTCompilationUnit(JJTCOMPILATIONUNIT);
    boolean jjtc000 = true;
    jjtree.openNodeScope(jjtn000);
    jjtn000.jjtSetFirstToken(getToken(1));init();
    try {
      label_2:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case IMPORT:{
          ;
          break;
          }
        default:
          break label_2;
        }
        ImportDeclaration();
      }
parseRender();
util.synchronize(ReactParserConstants.EOF);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 41:{
        jj_consume_token(41);
        break;
        }
      default:
        ;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case STUFF_TO_IGNORE:{
        jj_consume_token(STUFF_TO_IGNORE);
        break;
        }
      default:
        ;
      }
      jj_consume_token(0);
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

  /** Generated Token Manager. */
  public ReactParserTokenManager token_source;
  JavaCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;

  /** Constructor with InputStream. */
  public ReactParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public ReactParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new JavaCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new ReactParserTokenManager(jj_input_stream);
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
  public ReactParser(java.io.Reader stream) {
    jj_input_stream = new JavaCharStream(stream, 1, 1);
    token_source = new ReactParserTokenManager(jj_input_stream);
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
  public ReactParser(ReactParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
  }

  /** Reinitialise. */
  public void ReInit(ReactParserTokenManager tm) {
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
