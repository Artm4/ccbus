package ccbus.desktop.worker;

import ccbus.desktop.worker.WorkerCallback;
import ccbus.desktop.worker.WorkerCompute;

public interface IWorkerServiceCompute
{
    public <T>  void compute(WorkerCallback<T> callback, WorkerCompute<T> worker);
}
