package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.ASTFormalParameters;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.parser.java.ASTFormalParameter;
import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;

public class FormalParameters implements TreeTranslator
{
    public Node translate(Node node, TranslatedTree tree)
    {
        ASTFormalParameters nodeResult=
                new ASTFormalParameters(EcmaParserTreeConstants.JJTFORMALPARAMETERS);
        tree.add(nodeResult);

        ASTFormalParameter formalParameter=
                (ASTFormalParameter )
                node.findNextDownById(JavaParserTreeConstants.JJTFORMALPARAMETER,1);

        while(null!=formalParameter)
        {
            ccbus.tool.parser.ecmascript.ASTFormalParameter formalParameterResult=
                    (ccbus.tool.parser.ecmascript.ASTFormalParameter )
                tree.translate(JavaParserTreeConstants.JJTFORMALPARAMETER,formalParameter);
            nodeResult.add(formalParameterResult);
            formalParameter=(ASTFormalParameter)node.findNextDownById(JavaParserTreeConstants.JJTFORMALPARAMETER,1);
        }
        return nodeResult;
    }
}
