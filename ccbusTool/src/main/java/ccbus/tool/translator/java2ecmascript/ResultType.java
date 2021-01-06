package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.parser.java.ASTType;
import ccbus.tool.parser.java.ASTVoidType;
import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;

public class ResultType implements TreeTranslator {
    public Node translate(Node node, TranslatedTree tree)
    {
        ccbus.tool.parser.ecmascript.ASTResultType nodeResult=
                new ccbus.tool.parser.ecmascript.ASTResultType(EcmaParserTreeConstants.JJTRESULTTYPE);
        tree.add(nodeResult);

        ASTVoidType voidType=
                (ASTVoidType) node.findFirstDownById(JavaParserTreeConstants.JJTVOIDTYPE,1);
        if(null!=voidType)
        {
            nodeResult.add(tree.translateSingleToken(voidType,EcmaParserTreeConstants.JJTVOIDTYPE));
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
