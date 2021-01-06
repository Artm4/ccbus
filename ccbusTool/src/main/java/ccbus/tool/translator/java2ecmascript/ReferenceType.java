package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.parser.java.*;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;

public class ReferenceType implements TreeTranslator
{
    public Node translate(Node node,TranslatedTree tree)
    {
        return translate((ASTReferenceType) node,tree);
    }

    public Node translate(ASTReferenceType node,TranslatedTree tree)
    {
        ccbus.tool.parser.ecmascript.ASTReferenceType nodeResult=
                new ccbus.tool.parser.ecmascript.ASTReferenceType(EcmaParserTreeConstants.JJTREFERENCETYPE);
        tree.add(nodeResult);

        ASTPrimitiveType primitiveType=(ASTPrimitiveType)
                node.findFirstDownById(JavaParserTreeConstants.JJTPRIMITIVETYPE,1);

        ASTClassOrInterfaceType classOrInterfaceType=(ASTClassOrInterfaceType)
                node.findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACETYPE,1);

        if(null!=primitiveType)
        {
            ccbus.tool.parser.ecmascript.ASTPrimitiveType primitiveTypeResult=
                    (ccbus.tool.parser.ecmascript.ASTPrimitiveType)
            tree.translate(JavaParserTreeConstants.JJTPRIMITIVETYPE,primitiveType);
            nodeResult.add(primitiveTypeResult);
        }
        else
        if(null!=classOrInterfaceType)
        {
            ccbus.tool.parser.ecmascript.ASTClassOrInterfaceType classOrInterfaceTypeResult=
                    (ccbus.tool.parser.ecmascript.ASTClassOrInterfaceType)
            tree.translate(JavaParserTreeConstants.JJTCLASSORINTERFACETYPE,classOrInterfaceType);
            nodeResult.add(classOrInterfaceTypeResult);
        }

        node.resetNextSearch();
        for(int i=1;i<5;i++)
        {
            ASTBracket bracket=(ASTBracket)
                    node.findNextDownById(JavaParserTreeConstants.JJTBRACKET,2);

            if(null==bracket)
            {
                break;
            }

            ccbus.tool.parser.ecmascript.ASTBracket bracketResult=
                    (ccbus.tool.parser.ecmascript.ASTBracket)
            tree.translate(JavaParserTreeConstants.JJTBRACKET,bracket);

            nodeResult.add(bracketResult);
        }

        return nodeResult;
    }
}
