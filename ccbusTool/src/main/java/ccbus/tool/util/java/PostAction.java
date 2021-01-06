package ccbus.tool.util.java;

import java.util.function.BiFunction;

public class PostAction<T,U,R> implements Functor<R> {
    private final T value;
    private final U valueSecond;
    private BiFunction<T,U,R> func;
    public PostAction(T value, U valueSecond, BiFunction<T,U, R> f)
    {
        this.value = value; this.valueSecond=valueSecond; func = f;
    }
    public R apply()
    {
        return func.apply(value,valueSecond);
    }
}