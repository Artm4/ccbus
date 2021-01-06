import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

class ViewString
{
    private String impl;

    public ViewString()
    {
        impl="";
    }
    public ViewString(String str)
    {
        impl=str;
    }
    public int length()
    {
        return impl.length();
    }
    public char charAt(int index)
    {
        return impl.charAt(index);
    }

    public static Boolean compute(Integer a)
    {
        return true;
    }
}

class ViewInteger extends ViewString
{
    private Integer impl;

    public ViewInteger(Integer val)
    {
        super("");
        impl=val;
    }
    public ViewString toViewString()
    {
        return new ViewString(impl.toString());
    }
}

interface Worker<T>
{
    public void compute();
    public void onCompletion(T value);
    public void onError(Error error);
}

interface WorkerStorage
{
    public void compute();
    public <T> void  onCompletion(T value);
    public void onError(Error error);
}


// Rest worker populated outside
class WorkerServer
{
    public String[] someVal[];
    public float[] result,result2[];
    public int vals;
    public boolean[] valsa[]={{true,false},{true,false}};

    ArrayList<Integer> arr;
    HashMap<String,String> hash=new HashMap<>();
    int[] a=new int[]{12,3,4,5};

    public Function<Integer,Boolean> handleOnChange=this::compute;
    public Function<Integer,Boolean> handleOnChangeB=(Integer a) -> {a=0;return true;};
    public Function<Integer,Boolean> handleOnChangeC=ViewString::compute;
    public Function<Integer,Integer> handleOnChangeD=(Integer a) -> a+1;

    public WorkerServer()
    {
        handleOnChange=this::compute;
        handleOnChangeB=(Integer a) -> {a=0;return true;};
        handleOnChangeC=ViewString::compute;
    }
    // return or set result
    public void compute()
    {
        arr.size();
        arr.add(1);
        arr.get(0);
        arr.remove(0);

        hash.put("key","value");
        hash.containsKey("key");
        hash.get("key");
        hash.remove("key");
    }

    public Boolean compute(Integer a)
    {
        return true;
    }
}

@interface State {
}

@interface StateEnd {
}

@interface Literal {
}


@Literal
class SomeObject
{
    int objA=1;
    String str="";

    SomeObject()
    {

    }
}

public class TestEcmaProp
{
    @State
    public String[] someVal[];
    private float[] result,result2[];
    public int vals;
    public boolean[] valsa[]={{true,false},{true,false}};
    @StateEnd


    public static Object obj=new Object()
    {
        int objA=1;
        String str=""+"323423";
    };

    public static void someFunct()
    {
        ArrayList<String> arr = new ArrayList<>();
        //String i="";
        for (String i : arr)
        {
            String j = i + i;
        }
    }

    public SomeObject[] cals={
            new SomeObject()
            {
                public int objA=1;
                public String str="";
            },
            new SomeObject()
            {
                public int objA=1;
                public String str="";
            }
    };

    @Before
    public void setUpTest()
    {
        TestEcmaProp.someFunct();
        cals[0].objA=1;
        ViewString some=new ViewString("some");
         int vals=2;
        // Better
        new Worker<String>()
        {
            public void compute() {
                some.length();
            }

            public void onCompletion(String value) {

            }

            public void onError(Error error) {

            }
        };


        // do not know type in onCompletion

        new WorkerStorage()
        {
            {
                onCompletion("Some");
            }

            public void compute() {

            }

            public <T>void onCompletion(T value) {
                System.out.println(value);
            }

            public void onError(Error error) {

            }
        };
    }

    @Test
    public void testParsingDecl()
    {
        OutputStream a=new ByteArrayOutputStream();

        //JSONArray
    }
}
