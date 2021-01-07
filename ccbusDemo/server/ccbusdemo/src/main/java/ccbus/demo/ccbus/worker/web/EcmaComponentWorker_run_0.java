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

public class EcmaComponentWorker_run_0 extends WorkerServerImpl<String>
{
    public 
    ArrayList<String> a;
    public 
    SomePayload payload;
    public 
    DateTime dateTime;
    public  int page;
    public 
    String name;
    public  void compute()
            {
                a.add("My Name");
                a.add("My Name2");
                a.add(payload.toString());
                a.add(dateTime.toString());
                a.add(String.valueOf(page));
                name="New name Polya";
                complete(name);
            }
    
}