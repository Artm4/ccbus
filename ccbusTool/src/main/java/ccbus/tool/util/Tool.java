package ccbus.tool.util;

import java.util.Arrays;

public class Tool
{
    public <T> T[] mergeArrays(T[] a,T[] b)
    {
        T[] result = Arrays.copyOf(a, a.length + b.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
}
