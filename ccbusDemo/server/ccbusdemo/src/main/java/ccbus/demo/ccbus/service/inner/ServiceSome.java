package ccbus.demo.ccbus.service.inner;

import ccbus.connect.core.ccbus.WorkerService;
import ccbus.demo.ccbus.payload.SomePayload;


public class ServiceSome extends WorkerService<SomePayload>
{
    public String someVal="Hello";
    private int ival=1;

    @Override
    public void compute()
    {
        int a=2;
        int b=3;
        ival=2+3;
        String val=someVal;
        this.complete(new SomePayload(1,"name"));
    }

    public void setSomeVal(String someVal)
    {
        this.someVal = someVal;
    }
}
