package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;

public class TypeArguments implements TreeTranslator
{
    public Node translate(Node node, TranslatedTree tree)
    {
        ccbus.tool.parser.ecmascript.ASTTypeArguments nodeResult=
                new ccbus.tool.parser.ecmascript.ASTTypeArguments(EcmaParserTreeConstants.JJTTYPEARGUMENTS);
        tree.add(nodeResult);

        for(int i=0;i<10;i++)
        {
            Node nodeReference=node.findNextDownById(JavaParserTreeConstants.JJTREFERENCETYPE,2);
            if(null==nodeReference){break;}
            Node nodeReferenceResult=tree.translate(JavaParserTreeConstants.JJTREFERENCETYPE,nodeReference);

            nodeResult.add(nodeReferenceResult);
        }
        return nodeResult;
    }
}
