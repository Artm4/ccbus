package ccbus.connect.web.ccbusuiweb.components.input;

import ccbus.connect.core.ccbus.ExtComponent;
import ccbus.connect.ecma.Event;
import ccbus.connect.system.Prop;
import ccbus.connect.system.PropEnd;
import ccbus.connect.system.State;
import ccbus.connect.system.StateEnd;
import ccbus.connect.core.ccbus.payload.PageData;
import ccbus.connect.core.ccbus.payload.Pair;
import ccbus.connect.core.ccbus.payload.SingleValue;

import java.util.ArrayList;
import java.util.function.BiFunction;

public class RadioGroup extends ExtComponent
{
    @Prop
    public PageData<Pair> data=new PageData<>(new ArrayList<>());
    public SingleValue value=new SingleValue("");
    public String id="";
    public BiFunction<Object,Integer,Void> onChange=null;
    @PropEnd

    @State
    boolean render=false;
    @StateEnd

    void onChange(Event e, int index)
    {
        if(e.target.checked)
        {
            this.value.setValue(e.target.value);
        }
        else
        {
            this.value.setValue("");
        }
        if(this.onChange!=null)
        {
            this.onChange.apply(e,index);
        }
        this.stateKey("render",true);
    }
}
