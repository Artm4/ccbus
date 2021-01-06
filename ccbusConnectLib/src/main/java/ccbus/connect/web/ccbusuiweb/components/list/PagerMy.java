package ccbus.connect.web.ccbusuiweb.components.list;

import ccbus.connect.core.ccbus.ExtComponent;
import ccbus.connect.system.State;
import ccbus.connect.system.StateEnd;
import ccbus.connect.core.ccbus.payload.PageData;

import java.util.ArrayList;

public class PagerMy extends ExtComponent
{
    @State
    int page=1;
    @StateEnd

    PageData pageData=new PageData(new ArrayList<>());

    void some(int page)
    {
        page=1;
    }

    void onClick(String num)
    {

    }
}
