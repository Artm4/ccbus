package ccbus.connect.web.ccbusuiweb.components.input;

import ccbus.connect.core.ccbus.ExtComponent;
import ccbus.connect.ecma.Event;
import ccbus.connect.system.Prop;
import ccbus.connect.system.PropEnd;
import ccbus.connect.system.State;
import ccbus.connect.system.StateEnd;
import ccbus.connect.core.ccbus.payload.SingleValue;

public class Textarea  extends ExtComponent {
    @Prop
    public SingleValue value=new SingleValue("");

    public int cols=100;
    public int rows=6;
    public String placeholder;
    public int maxLength;
    public boolean readOnly;
    public boolean required;
    @PropEnd

    @State
    boolean render=false;
    @StateEnd

    void onChange(Event e)
    {
        this.value.setValue(e.target.value);
        this.stateKey("render",true);
    }
}
