package ccbus.dbtool.intermediate.routineparser;

import ccbus.dbtool.intermediate.ICodeNode;
import ccbus.dbtool.intermediate.RoutineCode;
import ccbus.dbtool.intermediate.icodeimpl.ICodeNodeClass;
import ccbus.dbtool.intermediate.routineimpl.ClassCode;
import ccbus.dbtool.intermediate.routineimpl.NodeCode;

public class NodeParser
{
    public NodeCode parse(ICodeNode node)
            throws Exception
    {
        return new NodeCode(node);
    }
}
