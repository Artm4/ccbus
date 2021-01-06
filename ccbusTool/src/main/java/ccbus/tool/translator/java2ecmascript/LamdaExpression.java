package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.ASTLamdaExpression;
import ccbus.tool.parser.ecmascript.ASTMethodDeclaration;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.translator.RecursiveHostTranslator;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.util.java.Tool;

public class LamdaExpression extends GenericTreeTranslator implements TreeTranslator,RecursiveHostTranslator {
    public Node translate(Node node, TranslatedTree tree)
    {
        ((Tool)tree.tool()).symTabStack().push(node.symTab());
        ASTLamdaExpression nodeResult=
                new ASTLamdaExpression(EcmaParserTreeConstants.JJTLAMDAEXPRESSION);

        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            Node child = tree.translateRecursive(node.jjtGetChild(i), this);
            nodeResult.add(child);
        }
        ((Tool)tree.tool()).symTabStack().pop();
        return nodeResult;
    }
}