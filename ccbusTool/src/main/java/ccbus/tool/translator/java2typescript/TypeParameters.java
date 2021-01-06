package ccbus.tool.translator.java2typescript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.typescript.ASTTypeParameters;
import ccbus.tool.parser.typescript.AngularParserConstants;
import ccbus.tool.parser.typescript.AngularParserTreeConstants;
import ccbus.tool.parser.java.*;

import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.parser.typescript.Token;
import ccbus.tool.translator.TranslatedTree;

public class TypeParameters implements TreeTranslator {
    public Node translate(Node node, TranslatedTree tree)
    {
        ASTTypeParameters parametersResult=new ASTTypeParameters(AngularParserTreeConstants.JJTTYPEPARAMETERS);
        tree.add(parametersResult);
        for(int i=0;i<10;i++)
        {
            ASTTypeParameter parameter=(ASTTypeParameter)node.findNextDownById(JavaParserTreeConstants.JJTTYPEPARAMETER,2);
            if(parameter==null){break;}
            ASTIdentifier identifier=(ASTIdentifier) parameter.jjtGetChild(0);
            Token tokenIdentifier= ccbus.tool.parser.typescript.Token.newToken(
                    AngularParserConstants.IDENTIFIER, identifier.jjtGetFirstToken().image);

            ccbus.tool.parser.typescript.ASTIdentifier identifierResult=
                    new ccbus.tool.parser.typescript.ASTIdentifier(AngularParserTreeConstants.JJTIDENTIFIER);
            identifierResult.jjtSetFirstToken(tokenIdentifier);

            ASTTypeParameter parameterResult=new ASTTypeParameter(AngularParserTreeConstants.JJTTYPEPARAMETER);
            parameterResult.jjtAddChild(identifierResult,0);

            parametersResult.jjtAddChild(parameterResult,i);
        }

        return parametersResult;
    }
}
