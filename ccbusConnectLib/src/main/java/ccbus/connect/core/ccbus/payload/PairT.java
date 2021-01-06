package ccbus.connect.core.ccbus.payload;

public class PairT<T,V>
{
    public T key;
    public V value;

    public PairT(T key, V value)
    {
        this.key = key;
        this.value = value;
    }

    public PairT()
    {
    }
}
