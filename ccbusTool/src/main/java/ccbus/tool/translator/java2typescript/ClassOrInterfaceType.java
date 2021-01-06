package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.ASTIdentifier;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;
import ccbus.tool.parser.java.ASTClassOrInterfaceType;
import ccbus.tool.parser.java.JavaParserTreeConstants;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.util.java.Tool;

public class ClassOrInterfaceType implements TreeTranslator
{
    public Node translate(Node node, TranslatedTree tree)
    {
        return translate((ASTClassOrInterfaceType) node,tree);
    }

    public Node translate(ASTClassOrInterfaceType node,TranslatedTree tree)
    {
        Tool tool=(Tool)tree.tool();
        ccbus.tool.parser.typescript.ASTClassOrInterfaceType nodeResult=
                new ccbus.tool.parser.typescript.ASTClassOrInterfaceType(AngularParserTreeConstants.JJTCLASSORINTERFACETYPE);
        tree.add(nodeResult);

        for(int i=0;i<10;i++)
        {
            Node identifier=node.findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER,1);
            if(null==identifier){break;}
            if(i>1)
            {
                //should throw error
                tool.errorTranslate(identifier,"Package prefix not allowed in name");
            }
            ASTIdentifier identifierResult=(ASTIdentifier)
            tree.translate(JavaParserTreeConstants.JJTIDENTIFIER,identifier);

            nodeResult.add(identifierResult);
        }

        for(int i=0;i<10;i++)
        {
            Node arguments=node.findNextDownById(JavaParserTreeConstants.JJTTYPEARGUMENTS,1);
            if(null==arguments){break;}
            Node argumentsResult=tree.translate(JavaParserTreeConstants.JJTTYPEARGUMENTS,arguments);

            nodeResult.add(argumentsResult);
        }
        return nodeResult;
    }
}
