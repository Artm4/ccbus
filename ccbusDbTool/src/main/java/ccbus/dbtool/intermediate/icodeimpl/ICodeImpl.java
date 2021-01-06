package ccbus.dbtool.intermediate.icodeimpl;
import ccbus.dbtool.intermediate.ICode;
import ccbus.dbtool.intermediate.ICodeNode;

public class ICodeImpl implements ICode
{
    private ICodeNode root;

    public ICodeImpl()
    {
        
    }
    public ICodeNode getRoot()
    {
        return root;
    }
    public ICodeNode setRoot(ICodeNode node)
    {
        root=node;
        return root;
    }
}
