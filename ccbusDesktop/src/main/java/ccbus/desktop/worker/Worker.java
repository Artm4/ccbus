package ccbus.desktop.worker;


import ccbus.connect.system.ResponseT;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Worker implements IWorkerServiceCompute
{
    static final ExecutorService executor = Executors.newCachedThreadPool();

    public static void shutdown()
    {
        executor.shutdown();
        try
        {
            executor.awaitTermination(5,TimeUnit.SECONDS);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public <T> void compute(WorkerCallback<T> callback, WorkerCompute<T> worker)
    {

        BackgroundTask<?> task=new BackgroundTask<ResponseT<T>>() {
            public ResponseT<T> compute() {
                return worker.compute();
            }

            protected void onCompletion(ResponseT<T> result, Throwable exception,
                                        boolean cancelled) {
                if(result.error.errno!=0 || result.error.error.length()>0)
                {
                    callback.onError(new WorkerError(result.error.error,result.error.errno));
                }
                else
                {
                    callback.onCompletion(result.payload);
                }

            }
        };
        executor.execute(task);
    }

}
