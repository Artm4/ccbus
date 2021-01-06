import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


class Token
{
    public int val=0;
}

class TokenE
{
    public int val=1;
}


class StorageScheme
{
    static Map<String,String[][][]> scheme=new HashMap<>();
    public static void init()
    {
        Some.init();
    }
}

class Some {
    public static final String entityName="Some Name";

    public static final String[][] entityList = {
            {"Some", "Value"},
            {"other", "other"}
    };

    public static final String[][] fieldList = {
            {"Some", "Value", "Some", "Value", "Some", "Value"},
            {"other", "other"}
    };

    public static final String[][] filterList = {
            {"Some", "Value"},
            {}
    };

    public static final String[][][] localSheme=
    {
            entityList,fieldList,filterList
    };

    public static void init()
    {
        StorageScheme.scheme.put(Some.entityName, localSheme);
    }
}

public class My {
    public static void main(String args[])
    {
        Date date=new Date();
        //2019-11-13T14:18:52.024Z
        //Wed Nov 13 16:22:56 EET 2019
        //System.out.println();
        StorageScheme.init();
        String[][][] scheme=StorageScheme.scheme.get(Some.entityName);
        int a=1;
        a++;

        TA b=new TA();
        b.add(new MyNumber(1));
        b.add(new MyNumber(2));
        b.add(new MyNumber(3));

        b.print();

        TA<HisNumber> ba=new TA<>();
        ba.add(new HisNumber(1));
        ba.add(new HisNumber(2));
        ba.add(new HisNumber(3));

        ba.print();


    }
}

class HisNumber
{
    int i;

    public HisNumber(int i)
    {
        this.i = i;
    }

    public String toString() {
        return 'A'+String.valueOf(i);
    }
}

class MyNumber extends HisNumber
{
    int fixed=1;

    public MyNumber(int i)
    {
        super(i);
        fixed++;
    }

    public boolean isFixed()
    {
        return fixed>1;
    }

    public String toString() {
        return String.valueOf(i);
    }

}

class TA<T extends HisNumber>
{
    ArrayList<T> arr = new ArrayList<>();
    TA()
    {

    }
    void print()
    {
        System.out.println(arr);
    }

    void add(T e)
    {
        arr.add(e);
    }
}

class TB extends TA<MyNumber>
{

    TB()
    {
        super();
    }
}
