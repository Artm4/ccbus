package ccbus.system.misc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CStructure {
    public <T> T[] mergeArrays(T[] a,T[] b)
    {
        T[] result = Arrays.copyOf(a, a.length + b.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    public static <T,E> Map<T,E> ofMap(T argA, E argB, Object ...args)
    {
        Map map=new HashMap<T,E>();
        map.put(argA,argB);
        for(int i=0;i<args.length;i+=2)
        {
            map.put(args[i],args[i+1]);
        }

        return map;
    }
}
