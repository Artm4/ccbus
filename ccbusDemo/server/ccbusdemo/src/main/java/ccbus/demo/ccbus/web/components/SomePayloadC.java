package ccbus.demo.ccbus.web.components;

import ccbus.connect.core.ccbus.Error;
import ccbus.connect.core.ccbus.WorkerServer;

public class SomePayloadC
{
    int a;
    String name;

    void run()
    {
        int b=2;
        new WorkerServer<String>()
        {
            Object bind=this;
            String nameB=name;
            // Server thread
            public void compute()
            {

                name="New name Polya";
                complete(name);
            }

            // UI thread
            public void onCompletion(String value)
            {
                name=value;
            }

            public void onError(Error error)
            {

            }

        }.complete("some");
    }
}
