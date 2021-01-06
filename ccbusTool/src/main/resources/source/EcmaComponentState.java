import packa.EcmaComp;

//import packa.*;
//import packa.packb.EcmaCompB;


public final class EcmaComponentState<T,E> extends Ext.Inner
{

    @State
    public int loadCart[];
    public int[] updateCart={1,2,3};
    public ArrayList<String> options;
    @StateEnd

    @State
    public int loadCartC=1;
    @StateEnd

    public int loadCartA=1;

    @Prop
    public int propCartB[]=0;
    public ArrayList<String> options;
    public EcmaCompB input;
    public ArrayList<String> options;
    public HashMap<Integer,String> classes;
    public Function<Integer,String> handleOnChange;
    public BiFunction<Integer,Object,String> handleOnChangeBi;
    @PropEnd

    public Function<Integer,Integer> handleOnChangeD=(Integer a) -> {return a+1;};
    public BiFunction<Integer,Object,String> handleOnChangeBi=(Object a,String b) -> {return b+1;};

    public ArrayList<String> options;
     public HashMap<String,String> hash=new HashMap<String,String>();
    int[] a=new int[]{12,3,4,5};
public BiFunction<Integer,Object,String> handleOnChangeBi;
int loadCartC;

    EcmaComp comp=new EcmaComp();
    public void setState()
    {

    }

    public void run()
    {

    }

    public void run(Event<T> event, Object other)
    {
        int loadCartA[]={1,2,3};
        comp.hash.put("key","value");
        comp.arr.add("a");
        comp.arr.add("b");
        comp.arr.remove(0);

        EcmaComp compB=new EcmaComp();
        compB.arr.add("a");

        ArrayList<String> arr=new ArrayList<String>();
        arr.add("some");
        hash.put("key","value");

       handleOnChangeBi=this::run;

        for(String i:arr)
        {
            String j;
            int a=i.length()+i.length();
        }
        loadCartC=2;
        loadCartA[0]=3;
        options.add(1);
        int b=2;
        int a=options.get(b);

        hash.put("key","value");
        if(hash.containsKey("key"))
        {
            hash.remove("key");
        }
        hash.get("key");

        EcmaComponentState.run();
        this.setState(new Object()
        {
            boolean isOpen= true;
        });

        return;
    }
}