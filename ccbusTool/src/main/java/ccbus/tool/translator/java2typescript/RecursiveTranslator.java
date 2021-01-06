package ccbus.tool.translator.java2typescript;

import ccbus.tool.parser.typescript.AngularParserTreeConstants;

import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.*;

import java.util.HashMap;

public class RecursiveTranslator extends RecursiveTranslatorImpl
{
    private HashMap<Integer, Integer> java2angularNodeId;
    private int[][] java2angularNodeIdInit = {
            {JavaParserTreeConstants.JJTASSIGNMENTOPERATOR, AngularParserTreeConstants.JJTASSIGNMENTOPERATOR},
            {JavaParserTreeConstants.JJTCONDITIONALEXPRESSION, AngularParserTreeConstants.JJTCONDITIONALEXPRESSION},
            {JavaParserTreeConstants.JJTCONDITIONALOREXPRESSION, AngularParserTreeConstants.JJTCONDITIONALOREXPRESSION},
            {JavaParserTreeConstants.JJTCONDITIONALANDEXPRESSION, AngularParserTreeConstants.JJTCONDITIONALANDEXPRESSION},
            {JavaParserTreeConstants.JJTINCLUSIVEOREXPRESSION, AngularParserTreeConstants.JJTINCLUSIVEOREXPRESSION},
            {JavaParserTreeConstants.JJTSWITCHLABEL, AngularParserTreeConstants.JJTSWITCHLABEL},
            {JavaParserTreeConstants.JJTSTATEMENTEXPRESSION, AngularParserTreeConstants.JJTSTATEMENTEXPRESSION},
            {JavaParserTreeConstants.JJTSWITCHSTATEMENT, AngularParserTreeConstants.JJTSWITCHSTATEMENT},
            {JavaParserTreeConstants.JJTSWITCHLABEL, AngularParserTreeConstants.JJTSWITCHLABEL},
            {JavaParserTreeConstants.JJTLBRACETOKEN, AngularParserTreeConstants.JJTLBRACETOKEN},
            {JavaParserTreeConstants.JJTRBRACETOKEN, AngularParserTreeConstants.JJTRBRACETOKEN},
            {JavaParserTreeConstants.JJTCASETOKEN, AngularParserTreeConstants.JJTCASETOKEN},
            {JavaParserTreeConstants.JJTFORSTATEMENT, AngularParserTreeConstants.JJTFORSTATEMENT},
            {JavaParserTreeConstants.JJTRPARENTOKEN, AngularParserTreeConstants.JJTRPARENTOKEN},
            {JavaParserTreeConstants.JJTARRAYINITIALIZER, AngularParserTreeConstants.JJTARRAYINITIALIZER},
            {JavaParserTreeConstants.JJTVARIABLEINITIALIZER, AngularParserTreeConstants.JJTVARIABLEINITIALIZER},
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