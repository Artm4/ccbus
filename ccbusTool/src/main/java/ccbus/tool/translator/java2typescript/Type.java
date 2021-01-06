package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;
import ccbus.tool.parser.java.ASTPrimitiveType;
import ccbus.tool.parser.java.ASTReferenceType;
import ccbus.tool.parser.java.ASTType;
import ccbus.tool.parser.java.JavaParserTreeConstants;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.TranslatedTree;
public class Type implements TreeTranslator
{
    public Node translate(Node node,TranslatedTree tree)
    {
        return translate((ASTType) node,tree);
    }
    public Node translate(ASTType node,TranslatedTree tree)
    {
        ccbus.tool.parser.typescript.ASTType nodeResult=
                new ccbus.tool.parser.typescript.ASTType(AngularParserTreeConstants.JJTTYPE);
        tree.add(nodeResult);

        ASTPrimitiveType primitiveType=(ASTPrimitiveType)
                node.findFirstDownById(JavaParserTreeConstants.JJTPRIMITIVETYPE,1);

        ASTReferenceType referenceType=(ASTReferenceType)
                node.findFirstDownById(JavaParserTreeConstants.JJTREFERENCETYPE,1);

        if(null!=primitiveType)
        {
            ccbus.tool.parser.typescript.ASTPrimitiveType primitiveTypeResult=
                    (ccbus.tool.parser.typescript.ASTPrimitiveType)
            tree.translate(JavaParserTreeConstants.JJTPRIMITIVETYPE,primitiveType);

            nodeResult.add(primitiveTypeResult);
            return nodeResult;
        }

        if(null!=referenceType)
        {
            ccbus.tool.parser.typescript.ASTReferenceType referenceTypeResult=
                    (ccbus.tool.parser.typescript.ASTReferenceType)
            tree.translate(JavaParserTreeConstants.JJTREFERENCETYPE,referenceType);

            nodeResult.add(referenceTypeResult);
            return nodeResult;
        }
        return nodeResult;
    }
}
