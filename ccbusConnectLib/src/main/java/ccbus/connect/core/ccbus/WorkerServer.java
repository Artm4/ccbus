package ccbus.connect.core.ccbus;

import ccbus.connect.system.ExportModule;

@ExportModule
public abstract class WorkerServer<T> implements IWorkerServer<T>
{
    public void complete(T r){}

    public Error error(){return null;}

    public void error(Error error){}

    public void error(String message){}
}

