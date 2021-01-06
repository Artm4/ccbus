package ccbus.connect.web.ccbusuiweb.components.input;

import ccbus.connect.core.ccbus.ExtComponent;
import ccbus.connect.core.ccbus.payload.PageData;
import ccbus.connect.core.ccbus.payload.Pair;
import ccbus.connect.core.ccbus.payload.SingleValue;
import ccbus.connect.ecma.Event;
import ccbus.connect.system.Prop;
import ccbus.connect.system.PropEnd;
import ccbus.connect.system.State;
import ccbus.connect.system.StateEnd;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Checkbox extends ExtComponent {
    @Prop
    public SingleValue value;
    public BiFunction<String,Event,Void> onChange;
    public String valueChecked="1";
    public String valueNotChecked="";
    public String id;
    @PropEnd

    @State
    boolean render=false;
    @StateEnd

    void renderOnChange(Event e)
    {

    }
}
