package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.parser.ecmascript.EcmaParserTreeConstants;
import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.*;

import java.util.HashMap;

public class RecursiveTranslator extends RecursiveTranslatorImpl
{
    private HashMap<Integer, Integer> java2angularNodeId;
    private int[][] java2angularNodeIdInit = {
            {JavaParserTreeConstants.JJTASSIGNMENTOPERATOR, EcmaParserTreeConstants.JJTASSIGNMENTOPERATOR},
            {JavaParserTreeConstants.JJTCONDITIONALEXPRESSION, EcmaParserTreeConstants.JJTCONDITIONALEXPRESSION},
            {JavaParserTreeConstants.JJTCONDITIONALOREXPRESSION, EcmaParserTreeConstants.JJTCONDITIONALOREXPRESSION},
            {JavaParserTreeConstants.JJTCONDITIONALANDEXPRESSION, EcmaParserTreeConstants.JJTCONDITIONALANDEXPRESSION},
            {JavaParserTreeConstants.JJTINCLUSIVEOREXPRESSION, EcmaParserTreeConstants.JJTINCLUSIVEOREXPRESSION},
            {JavaParserTreeConstants.JJTSWITCHLABEL, EcmaParserTreeConstants.JJTSWITCHLABEL},
            {JavaParserTreeConstants.JJTSTATEMENTEXPRESSION, EcmaParserTreeConstants.JJTSTATEMENTEXPRESSION},
            {JavaParserTreeConstants.JJTSWITCHSTATEMENT, EcmaParserTreeConstants.JJTSWITCHSTATEMENT},
            {JavaParserTreeConstants.JJTSWITCHLABEL, EcmaParserTreeConstants.JJTSWITCHLABEL},
            {JavaParserTreeConstants.JJTLBRACETOKEN, EcmaParserTreeConstants.JJTLBRACETOKEN},
            {JavaParserTreeConstants.JJTRBRACETOKEN, EcmaParserTreeConstants.JJTRBRACETOKEN},
            {JavaParserTreeConstants.JJTCASETOKEN, EcmaParserTreeConstants.JJTCASETOKEN},
            {JavaParserTreeConstants.JJTFORSTATEMENT, EcmaParserTreeConstants.JJTFORSTATEMENT},
            {JavaParserTreeConstants.JJTRPARENTOKEN, EcmaParserTreeConstants.JJTRPARENTOKEN},
            {JavaParserTreeConstants.JJTARRAYINITIALIZER, EcmaParserTreeConstants.JJTARRAYINITIALIZER},
            {JavaParserTreeConstants.JJTVARIABLEINITIALIZER, EcmaParserTreeConstants.JJTVARIABLEINITIALIZER},
            {JavaParserTreeConstants.JJTNEWTOKEN, EcmaParserTreeConstants.JJTNEWTOKEN},
            {JavaParserTreeConstants.JJTEXTENDSLIST, EcmaParserTreeConstants.JJTEXTENDSLIST},
            {JavaParserTreeConstants.JJTARROWTOKEN, EcmaParserTreeConstants.JJTARROWTOKEN},
            {JavaParserTreeConstants.JJTLAMDAEXPRESSION, EcmaParserTreeConstants.JJTLAMDAEXPRESSION},
            {JavaParserTreeConstants.JJTLAMDABODY, EcmaParserTreeConstants.JJTLAMDABODY},
            {JavaParserTreeConstants.JJTFORMALPARAMETERS, EcmaParserTreeConstants.JJTFORMALPARAMETERS},
            {JavaParserTreeConstants.JJTRETURNSTATEMENT, EcmaParserTreeConstants.JJTRETURNSTATEMENT},
            {JavaParserTreeConstants.JJTRETURNTOKEN, EcmaParserTreeConstants.JJTRETURNTOKEN},
            {JavaParserTreeConstants.JJTPRIMITIVETYPE, EcmaParserTreeConstants.JJTPRIMITIVETYPE},
            {JavaParserTreeConstants.JJTTYPEARGUMENTS, EcmaParserTreeConstants.JJTTYPEARGUMENTS},
            {JavaParserTreeConstants.JJTLITERAL, EcmaParserTreeConstants.JJTLITERAL},
            {JavaParserTreeConstants.JJTCASTEXPRESSION, EcmaParserTreeConstants.JJTCASTEXPRESSION},
    };

    public RecursiveTranslator() {
        java2angularNodeId = new HashMap<Integer, Integer>();
        for (int i = 0; i < java2angularNodeIdInit.length; i++) {
            java2angularNodeId.put(java2angularNodeIdInit[i][0], java2angularNodeIdInit[i][1]);
        }
    }

    public int getToId(int fromId) {
        return java2angularNodeId.containsKey(fromId) ? java2angularNodeId.get(fromId) : 0;
    }
}