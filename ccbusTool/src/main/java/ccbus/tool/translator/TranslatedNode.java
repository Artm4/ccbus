package ccbus.tool.translator;

import ccbus.tool.intermediate.Node;
import ccbus.tool.intermediate.SimpleNode;
import ccbus.tool.intermediate.Token;

public class TranslatedNode extends SimpleNode {
    private Node node;

    public TranslatedNode(Node n) {
        super(n.getId());
        node=n;
    }

    public Token jjtGetFirstToken() { return node.jjtGetFirstToken(); }
    public void jjtSetFirstToken(Token token) { node.jjtSetFirstToken(token); }
    public Token jjtGetLastToken() { return node.jjtGetLastToken(); }
    public void jjtSetLastToken(Token token) { node.jjtSetLastToken(token); }
    public int getId() {return node.getId();}
    public String image(){return node.image();}
    public Node getNode(){return node;}
}
