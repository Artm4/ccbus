package ccbus.desktop.worker;

import ccbus.desktop.worker.WorkerCallback;

public interface IWorkerService <T>
{
    public  void compute(WorkerCallback<T> callback);
}

