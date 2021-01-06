package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.ASTMethodDeclarator;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.parser.java.ASTFormalParameters;
import ccbus.tool.parser.java.ASTIdentifier;
import ccbus.tool.parser.java.ASTTypeParameters;
import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;

public class MethodDeclarator implements TreeTranslator {
    public Node translate(Node node, TranslatedTree tree)
    {
        ASTMethodDeclarator nodeResult=
                new ASTMethodDeclarator(EcmaParserTreeConstants.JJTMETHODDECLARATOR);
        tree.add(nodeResult);

        ASTIdentifier nodeIdentifier=(ASTIdentifier)
                node.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,1);
        ASTTypeParameters  nodeTypeParameters= (ASTTypeParameters)
                node.findFirstDownById(JavaParserTreeConstants.JJTTYPEPARAMETERS,1);
        ASTFormalParameters nodeFormalParameters=(ASTFormalParameters)
                node.findFirstDownById(JavaParserTreeConstants.JJTFORMALPARAMETERS,1);

        ccbus.tool.parser.ecmascript.ASTIdentifier identifierResult=
                (ccbus.tool.parser.ecmascript.ASTIdentifier)
            tree.translate(JavaParserTreeConstants.JJTIDENTIFIER,nodeIdentifier);
        nodeResult.add(identifierResult);

        if(null!=nodeTypeParameters)
        {
            ccbus.tool.parser.ecmascript.ASTTypeParameters typeParametersResult=
                    (ccbus.tool.parser.ecmascript.ASTTypeParameters)
                tree.translate(JavaParserTreeConstants.JJTTYPEPARAMETERS,nodeTypeParameters);
            nodeResult.add(typeParametersResult);
        }

        ccbus.tool.parser.ecmascript.ASTFormalParameters formalParametersResult=
                (ccbus.tool.parser.ecmascript.ASTFormalParameters)
        tree.translate(JavaParserTreeConstants.JJTFORMALPARAMETERS,nodeFormalParameters);
        nodeResult.add(formalParametersResult);

        return nodeResult;
    }
}