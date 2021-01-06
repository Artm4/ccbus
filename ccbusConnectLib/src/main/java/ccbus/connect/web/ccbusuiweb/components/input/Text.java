package ccbus.connect.web.ccbusuiweb.components.input;

import ccbus.connect.core.ccbus.ExtComponent;
import ccbus.connect.ecma.Event;
import ccbus.connect.system.Prop;
import ccbus.connect.system.PropEnd;
import ccbus.connect.system.State;
import ccbus.connect.system.StateEnd;
import ccbus.connect.core.ccbus.payload.SingleValue;

public class Text  extends ExtComponent {
    @Prop
    public SingleValue value=new SingleValue("");
    public String name;
    public String type="text";

    public int size;
    public int maxLength;
    public String placeholder;
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
