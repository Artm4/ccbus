package ccbus.connect.core.ccbus;


public interface IWorkerServer<T>
{
    public void compute();

    public void onCompletion(T value);

    public void onError(Error error);

}
