import packa.EcmaComp;
import packa.*;
import packa.packb.EcmaCompB;

//import java.util.ArrayList;

public final class EcmaComponent<T,E> extends Ext.Inner
{
    public static int someStatic=1;
    public ViewString<Some,Other<T>>[] someVal[];
     private float[] result,result2[];
     public ViewInteger vals=5;
    public boolean[] valsa[]={{true,false},{true,false}};

    @State
    public int loadCart;
    public int[] updateCart={1,2,3};
    public ArrayList<String> options;
    @StateEnd

    @State
    public int loadCartB=1;
    @StateEnd

    private void onClick(int[] aa[])
    {
        this.loadCart=1;
        updateCart[0]=3;
        Object aobj=new Object()
        {
            int objA=1;
            String str="";
            int valPred=EcmaComponent.someStatic;
            ArrayList<String> options;
        };
        this.run(aobj,1);
        this.someVal=new Object(1,2,3);
        String some[];
        int vals[][];
        int a=1;
        int b=2;
        b=a+1;
        boolean c;
        c=(true || false)/(1+1);
        this.result=c;
        if(c)
        {
            c=false;
            c=true;
            b=b+2;
        }
        else
        {
            b=b+2;
        }

        b=0;
        while(b<100)
        {
            b=b+1;
        }

        do
        {
             b--;
        }
        while(b>0);
        try
        {
            a=a-1;
        }
        catch(Error e)
        {
            a=-1;
            do
            {
                b--;
            }
            while(b>0);
        }
        finally {
            a=0;
        }

        switch(b)
        {
            case 1:
                a = -1;
                a = -2;
            {
                a=2;
                {
                    a=2;
                }
            }
            default:
                break;

        }

        for(int i=1;i<10;i++)
        {
            int a=a+1;
        }

        int[] arr={1,2,3};
        for(int i:arr)
        {
            int a=a+1;
            this.result[i]=a;
        }
    }

    String toString(String b)
    {
        //return "";
    }

    public void run(Event<T> event)
    {

    }
}