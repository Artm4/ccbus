package ccbus.dbtool.util.console;

import java.util.Arrays;
import java.util.List;

public class ConsoleOptionTuple
{
    public static List<ConsoleOption> hasArgumentList=
            Arrays.asList(ConsoleOption.CONFIG_S,ConsoleOption.GEN_S);

    public ConsoleOption option;
    public String param="";

    public ConsoleOptionTuple(ConsoleOption option, String param)
    {
        this.option = option;
        this.param = param;
    }

    public ConsoleOptionTuple(ConsoleOption option)
    {
        this.option = option;
    }

    public ConsoleOption getOption()
    {
        return option;
    }

    public void setOption(ConsoleOption option)
    {
        this.option = option;
    }

    public String getParam()
    {
        return param;
    }

    public void setParam(String param)
    {
        this.param = param;
    }

    public boolean hasArgument()
    {
        return hasArgumentList.contains(option);
    }
}
