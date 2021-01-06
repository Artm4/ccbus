package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.ASTTypeParameters;
import ccbus.tool.parser.ecmascript.EcmaParserConstants;
import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.parser.ecmascript.Token;
import ccbus.tool.parser.java.ASTIdentifier;
import ccbus.tool.parser.java.ASTTypeParameter;
import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;

public class TypeParameters implements TreeTranslator {
    public Node translate(Node node, TranslatedTree tree)
    {
        ASTTypeParameters parametersResult=new ASTTypeParameters(EcmaParserTreeConstants.JJTTYPEPARAMETERS);
        tree.add(parametersResult);
        for(int i=0;i<10;i++)
        {
            ASTTypeParameter parameter=(ASTTypeParameter)node.findNextDownById(JavaParserTreeConstants.JJTTYPEPARAMETER,2);
            if(parameter==null){break;}
            ASTIdentifier identifier=(ASTIdentifier) parameter.jjtGetChild(0);
            Token tokenIdentifier=Token.newToken(
                    EcmaParserConstants.IDENTIFIER, identifier.jjtGetFirstToken().image);

            ccbus.tool.parser.ecmascript.ASTIdentifier identifierResult=
                    new ccbus.tool.parser.ecmascript.ASTIdentifier(EcmaParserTreeConstants.JJTIDENTIFIER);
            identifierResult.jjtSetFirstToken(tokenIdentifier);

            ASTTypeParameter parameterResult=new ASTTypeParameter(EcmaParserTreeConstants.JJTTYPEPARAMETER);
            parameterResult.jjtAddChild(identifierResult,0);

            parametersResult.jjtAddChild(parameterResult,i);
        }

        return parametersResult;
    }
}
