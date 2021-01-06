package ccbus.connect.core.ccbus;

import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class WorkerService<T> implements IWorkerService<T>
{
    public void complete(T r){}

    public Error error(){return null;}

    public void error(Error error){}

    public void error(String message){}
    public void compute(Function onComplete, Function onError){}
}
