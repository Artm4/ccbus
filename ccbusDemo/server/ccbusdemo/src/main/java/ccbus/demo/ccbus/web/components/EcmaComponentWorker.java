package ccbus.demo.ccbus.web.components;


import ccbus.connect.core.ccbus.Error;
import ccbus.connect.core.ccbus.ExtComponent;
import ccbus.connect.core.ccbus.WorkerServer;
import ccbus.connect.core.ccbus.components.Pager;
import ccbus.connect.ecma.Date;
import ccbus.connect.system.Prop;
import ccbus.connect.system.PropEnd;
import ccbus.connect.system.State;
import ccbus.connect.system.StateEnd;

import ccbus.demo.ccbus.payload.SomePayload;
import ccbus.connect.core.ccbus.payload.PageData;
import ccbus.demo.ccbus.payload.packp.OtherPayload;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import ccbus.demo.ccbus.web.components.EcmaInner;
import ccbus.demo.ccbus.service.ServiceSome;


public class EcmaComponentWorker extends Pager
{
    @State
    String name="Some Vla";
    ArrayList<String> a=new ArrayList<>();
    ArrayList<Integer> aa=new ArrayList<>();
    @StateEnd

    Date date=new Date("2012-01-02 13:00");
    DateTime dateTime=new DateTime("2012-01-02 13:00");

    //ArrayList<String> av=new ArrayList<>();
    String nameB="Some Vla";
    SomePayload payload=new SomePayload();
    OtherPayload payloadB=new OtherPayload();
    public BiFunction<Object,Object,String> handleOnChangeBi;
    public Function<Integer,Integer> handleOnChangeD=(Integer b) -> {return b+1;};

    int someInteger;

    public EcmaComponentWorker()
    {
        super();
    }


    @Override
    public void componentDidMount()
    {
        run();
    }

    public String some(Object a, Object b)
    {
        return "";
    }
    public String someS(Object a)
    {
        return "";
    }

    public void run()
   {

       this.page=2;
       this.stateKey("name","Some new val");
      this.handleOnChangeD.apply(1);
       handleOnChangeBi=this::some;
       handleOnChangeBi.apply(new Object(),payload);

       ServiceSome serviceSome=new ServiceSome();
       serviceSome.compute(this::someS,this::someS);

        HashMap<String,String> b=new HashMap<>();
      new WorkerServer<String>()
        {
            Object bind=this;
            String nameB=name;
            // Server thread
            public void compute()
            {
                a.add("My Name");
                a.add("My Name2");
                a.add(payload.toString());
                a.add(dateTime.toString());
                a.add(String.valueOf(page));
                name="New name Polya";
                complete(name);
            }

            // UI thread
            public void onCompletion(String value)
            {
                stateKey("name",value);
            }

            public void onError(Error error)
            {

            }

        };

//       new WorkerStorage<String>()
//       {
//
//           // Server thread
//           public void key()
//           {
//               this.key(payload.name);
//           }
//           // UI thread
//           public void onCompletion(String value)
//           {
//               stateKey("name",value);
//           }
//
//           public void onError(Error error)
//           {
//
//           }
//
//       };
//
//       new WorkerCache<String>()
//       {
//
//           // Server thread
//           public void key()
//           {
//               this.key("20/02/2012"+name);
//           }
//
//           // Server thread
//           public void compute()
//           {
//               a.add("My Name");
//               a.add("My Name2");
//               a.add(payload.toString());
//               a.add(dateTime.toString());
//               a.add(String.valueOf(page));
//               name="New name Polya";
//               complete(name);
//           }
//
//           // UI thread
//           public void onCompletion(String value)
//           {
//               stateKey("name",value);
//           }
//
//           public void onError(Error error)
//           {
//
//           }
//
//       };

        new WorkerServer<ArrayList>()
        {
            // Server thread
            public void compute()
            {
                String some=name;
                ArrayList<Integer> result= new ArrayList<>();
                result.addAll(Arrays.asList(1,2,3,4,5));
                complete(result);
            }

            // UI thread
            public void onCompletion(ArrayList value)
            {
                stateKey("aa",value);
            }

            public void onError(Error error)
            {

            }
        };
    }
}