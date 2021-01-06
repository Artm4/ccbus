package ccbus.desktop.worker;

public interface IWorkerCallback<T> {

    void onCompletion(T var1);

    void onError(WorkerError var1);
}
