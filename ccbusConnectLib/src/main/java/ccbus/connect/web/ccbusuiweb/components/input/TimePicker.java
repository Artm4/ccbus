package ccbus.connect.web.ccbusuiweb.components.input;

import ccbus.connect.core.ccbus.ExtComponent;
import ccbus.connect.core.ccbus.lib.ParseDate;
import ccbus.connect.core.ccbus.payload.PageData;
import ccbus.connect.core.ccbus.payload.Pair;
import ccbus.connect.core.ccbus.payload.SingleValue;
import ccbus.connect.system.Prop;
import ccbus.connect.system.PropEnd;
import ccbus.connect.system.State;
import ccbus.connect.system.StateEnd;

import java.util.ArrayList;
import java.util.Date;
import java.util.function.BiFunction;
import java.util.function.Function;

public class TimePicker  extends ExtComponent {
    @Prop
    Date value=ParseDate.createUTCZeroTime();
    int stepHours=1;
    int stepMinutes=1;
    public Function<Date,Void> onChange=null;
    boolean enableUTC=false;
    @PropEnd

    @State
    boolean render=false;
    @StateEnd
    PageData<Pair> hourData=new PageData<Pair>(new ArrayList<>());
    PageData<Pair> minuteData=new PageData<Pair>(new ArrayList<>());

    SingleValue hourValue=new SingleValue("0");
    SingleValue minuteValue=new SingleValue("0");

    Date _date=ParseDate.createUTCZeroTime();

    TimePicker(Object props)
    {
        super(props);
        renderInit(props);
    }

    void renderInit(Object props)
    {

    }
}
