package ccbus.connect.web.ccbusuiweb.components.input;

import ccbus.connect.core.ccbus.ExtComponent;
import ccbus.connect.ecma.Event;
import ccbus.connect.system.Prop;
import ccbus.connect.system.PropEnd;
import ccbus.connect.system.State;
import ccbus.connect.system.StateEnd;
import ccbus.connect.core.ccbus.payload.*;

import java.util.ArrayList;
import java.util.function.Function;

public class AutoComplete  extends ExtComponent {
    @Prop
    public PageData<IKeyData> data=new PageData<>(new ArrayList<>());
    public Function<String,Void> onSearch=(String str)->{return null;};
    public int maxSearchLength=2;
    public MultiValueKey value=new MultiValueKey(new ArrayList());
    @PropEnd

    @State
    boolean render=false;
    @StateEnd

    boolean expandDropDownFlag=false;
    public Ref node;
    PageDataMap pageDataMap;

    Function<Event,Boolean> handleSelect=(Event event) ->
    {
        if (this.node.current.contains(event.target)) {
            // inside click
            return true;
        }
        this.collapseDropDown();
        return false;
    };

    @Override
    public void componentDidMount() {
        getDocument().addEventListener("mousedown",this.handleSelect);
    }

    @Override
    public void componentWillUnmount() {
        getDocument().removeEventListener("mousedown",this.handleSelect);
    }

    AutoComplete(Object props)
    {
        super(props);
        this.node=createRef();
        onChangeProp("data",(Object a,Object b)->{
            return null;
        });
    }

    void onChange(Event event)
    {
        if(event.target.value.length()>=this.maxSearchLength)
        {
            onSearch.apply(event.target.value);
        }
    }

    void onSelect(Event event,Pair item)
    {
        value.setValue(item);
        collapseDropDown();
        event.stopPropagation();
    }

    void onRemove(Event event,Pair item)
    {
        value.removeValue(item);
        this.stateKey("render",!this.render);
    }

    void onRemoveAll(Event event)
    {
        value.removeAll();
        this.stateKey("render",!this.render);
    }

    void expandDropDown()
    {
        this.expandDropDownFlag=true;
        this.stateKey("render",!this.render);
    }

    void collapseDropDown()
    {
        this.expandDropDownFlag=false;
        this.stateKey("render",!this.render);
    }
}
