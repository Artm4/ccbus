package ccbus.connect.core.ccbus;


import java.util.function.Function;

public interface IWorkerService<T>
{
    public void compute();
    public void compute(Function onComplete, Function onError);

}
