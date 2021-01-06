package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.java.ASTType;
import ccbus.tool.parser.java.ASTVoidType;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;
import ccbus.tool.parser.java.JavaParserTreeConstants;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.TranslatedTree;

public class ResultType implements TreeTranslator {
    public Node translate(Node node, TranslatedTree tree)
    {
        ccbus.tool.parser.typescript.ASTResultType nodeResult=
                new ccbus.tool.parser.typescript.ASTResultType(AngularParserTreeConstants.JJTRESULTTYPE);
        tree.add(nodeResult);

        ASTVoidType voidType=
                (ASTVoidType) node.findFirstDownById(JavaParserTreeConstants.JJTVOIDTYPE,1);
        if(null!=voidType)
        {
            nodeResult.add(tree.translateSingleToken(voidType,AngularParserTreeConstants.JJTVOIDTYPE));
        }
        ASTType type=
                (ASTType) node.findFirstDownById(JavaParserTreeConstants.JJTTYPE,1);
        if(null!=type)
        {
            nodeResult.add(tree.translate(JavaParserTreeConstants.JJTTYPE,type));
        }
        return nodeResult;
    }
}
