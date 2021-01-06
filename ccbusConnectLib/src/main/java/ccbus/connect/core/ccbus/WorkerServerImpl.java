package ccbus.connect.core.ccbus;

import ccbus.connect.system.ExportModule;

@ExportModule
public class WorkerServerImpl<T>
{
    private Error error=new Error();
    private Object result=null;

    public Error error()
    {
        return error;
    }

    public void error(Error error)
    {
        this.error = error;
    }

    public void error(String message)
    {
        this.error = new Error(message);
    }

    public void compute(){}

    public void complete(T r)
    {
        result=r;
    }

    public Object result()
    {
        return result;
    }

}
