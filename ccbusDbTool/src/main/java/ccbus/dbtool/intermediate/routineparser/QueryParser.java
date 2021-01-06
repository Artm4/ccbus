package ccbus.dbtool.intermediate.routineparser;

import ccbus.dbtool.intermediate.ICodeNode;
import ccbus.dbtool.intermediate.icodeimpl.ICodeNodeQuery;
import ccbus.dbtool.intermediate.routineimpl.NodeCode;

public class QueryParser extends NodeParser
{
    public NodeCode parse(ICodeNodeQuery node)
            throws Exception
    {
        return new NodeCode(node);
    }
}
