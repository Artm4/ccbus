package ccbus.demo.ccbus.web.components;

import ccbus.connect.core.ccbus.ExtComponent;
import ccbus.connect.system.Prop;
import ccbus.connect.system.PropEnd;
import ccbus.connect.system.State;
import ccbus.connect.system.StateEnd;

import java.util.function.Function;

public class EcmaInner extends ExtComponent {
    @Prop
    String propName="";
    @PropEnd

    @State
    String stateName="";
    @StateEnd

    public Function<Integer,Integer> handleOnChangeD=(Integer b) -> {return b+1;};

    public EcmaInner() {
        super();
        onChangeProp("propName",(Object prop,Object propPrev) ->
        {
            this.stateKey("stateName",prop);
            return 0;
        });
    }
}
