package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.ASTMethodDeclarator;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;
import ccbus.tool.parser.java.*;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.TranslatedTree;

public class MethodDeclarator implements TreeTranslator {
    public Node translate(Node node, TranslatedTree tree)
    {
        ASTMethodDeclarator nodeResult=
                new ASTMethodDeclarator(AngularParserTreeConstants.JJTMETHODDECLARATOR);
        tree.add(nodeResult);

        ASTIdentifier nodeIdentifier=(ASTIdentifier)
                node.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,1);
        ASTTypeParameters  nodeTypeParameters= (ASTTypeParameters)
                node.findFirstDownById(JavaParserTreeConstants.JJTTYPEPARAMETERS,1);
        ASTFormalParameters nodeFormalParameters=(ASTFormalParameters)
                node.findFirstDownById(JavaParserTreeConstants.JJTFORMALPARAMETERS,1);

        ccbus.tool.parser.typescript.ASTIdentifier identifierResult=
                (ccbus.tool.parser.typescript.ASTIdentifier)
            tree.translate(JavaParserTreeConstants.JJTIDENTIFIER,nodeIdentifier);
        nodeResult.add(identifierResult);

        if(null!=nodeTypeParameters)
        {
            ccbus.tool.parser.typescript.ASTTypeParameters typeParametersResult=
                    (ccbus.tool.parser.typescript.ASTTypeParameters)
                tree.translate(JavaParserTreeConstants.JJTTYPEPARAMETERS,nodeTypeParameters);
            nodeResult.add(typeParametersResult);
        }

        ccbus.tool.parser.typescript.ASTFormalParameters formalParametersResult=
                (ccbus.tool.parser.typescript.ASTFormalParameters)
        tree.translate(JavaParserTreeConstants.JJTFORMALPARAMETERS,nodeFormalParameters);
        nodeResult.add(formalParametersResult);

        return nodeResult;
    }
}