package ccbus.tool.translator.java2reactserverjava;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.java.ASTName;
import ccbus.tool.parser.java.ASTPackageDeclaration;
import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.util.java.Tool;

public class PackageDeclaration
        implements TreeTranslator
{
    private Tool tool;

    public Node translate(Node node, TranslatedTree tree)
    {
        ASTName name = (ASTName)
                node.findFirstDownById(JavaParserTreeConstants.JJTNAME);

        tool = ((Tool) tree.tool());
        if(tool.isClientPackage(name))
        {
            tool.insertImport(node,
                    tool.createImport(
                            tool.createName("ccbus","connect","core","react","React")
                    )
            );
        }
        return null;
    }
}
