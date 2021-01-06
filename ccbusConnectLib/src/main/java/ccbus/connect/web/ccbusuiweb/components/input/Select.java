package ccbus.connect.web.ccbusuiweb.components.input;

import ccbus.connect.core.ccbus.ExtComponent;
import ccbus.connect.ecma.Event;
import ccbus.connect.system.Prop;
import ccbus.connect.system.PropEnd;
import ccbus.connect.core.ccbus.payload.PageData;
import ccbus.connect.core.ccbus.payload.Pair;
import ccbus.connect.core.ccbus.payload.SingleValue;

import java.util.ArrayList;
import java.util.function.Function;

public class Select extends ExtComponent
{
    @Prop
    public SingleValue value;
    public PageData<Pair> data=new PageData<>(new ArrayList<>());
    public Function<Event,Void>  onChange;
    @PropEnd

    void onChange(Event e)
    {
        this.value.setValue(e.target.value);
        onChange.apply(e);
    }
}
