package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;
import ccbus.tool.parser.java.JavaParserTreeConstants;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.TranslatedTree;

public class TypeArguments implements TreeTranslator
{
    public Node translate(Node node, TranslatedTree tree)
    {
        ccbus.tool.parser.typescript.ASTTypeArguments nodeResult=
                new ccbus.tool.parser.typescript.ASTTypeArguments(AngularParserTreeConstants.JJTTYPEARGUMENTS);
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
