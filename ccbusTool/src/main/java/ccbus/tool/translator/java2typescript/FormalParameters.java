package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.ASTFormalParameters;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;
import ccbus.tool.parser.java.ASTFormalParameter;
import ccbus.tool.parser.java.JavaParserTreeConstants;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.translator.TranslatedTree;

public class FormalParameters implements TreeTranslator
{
    public Node translate(Node node, TranslatedTree tree)
    {
        ASTFormalParameters nodeResult=
                new ASTFormalParameters(AngularParserTreeConstants.JJTFORMALPARAMETERS);
        tree.add(nodeResult);

        ASTFormalParameter formalParameter=
                (ASTFormalParameter )
                node.findNextDownById(JavaParserTreeConstants.JJTFORMALPARAMETER,1);

        while(null!=formalParameter)
        {
            ccbus.tool.parser.typescript.ASTFormalParameter formalParameterResult=
                    (ccbus.tool.parser.typescript.ASTFormalParameter )
                tree.translate(JavaParserTreeConstants.JJTFORMALPARAMETER,formalParameter);
            nodeResult.add(formalParameterResult);
            formalParameter=(ASTFormalParameter)node.findNextDownById(JavaParserTreeConstants.JJTFORMALPARAMETER,1);
        }
        return nodeResult;
    }
}
