package ccbus.demo.ccbus.worker.web;

import ccbus.connect.core.ccbus.WorkerServerImpl;


import ccbus.connect.core.ccbus.Error;
import ccbus.connect.core.ccbus.ExtComponent;
import ccbus.connect.core.ccbus.WorkerServer;
import ccbus.connect.core.ccbus.components.Pager;
import ccbus.demo.ccbus.payload.SomePayload;
import ccbus.connect.core.ccbus.payload.PageData;
import ccbus.demo.ccbus.payload.packp.OtherPayload;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import ccbus.connect.core.react.React;

public class EcmaComponentWorker_run_1 extends WorkerServerImpl<ArrayList>
{
    public 
    String name;
    public  void compute()
            {
                String some=name;
                ArrayList<Integer> result= new ArrayList<>();
                result.addAll(Arrays.asList(1,2,3,4,5));
                complete(result);
            }
    
}