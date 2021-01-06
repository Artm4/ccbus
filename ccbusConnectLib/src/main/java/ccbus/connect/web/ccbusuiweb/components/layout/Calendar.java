package ccbus.connect.web.ccbusuiweb.components.layout;

import ccbus.connect.core.ccbus.ExtComponent;
import ccbus.connect.system.Prop;
import ccbus.connect.core.ccbus.payload.PageData;

import java.util.ArrayList;

public class Calendar extends ExtComponent {
    @Prop
    public PageData<Object> data=new PageData<>(new ArrayList<>());
}
