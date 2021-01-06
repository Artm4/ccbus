package ccbus.connect.web.ccbusuiweb.components;

import ccbus.connect.core.ccbus.ExtComponent;
import ccbus.connect.ecma.Number;
import ccbus.connect.system.Prop;
import ccbus.connect.system.PropEnd;
import ccbus.connect.system.State;
import ccbus.connect.system.StateEnd;
import ccbus.connect.core.ccbus.payload.PageData;

import java.util.ArrayList;
import java.util.function.Function;

public class Paginator extends ExtComponent
{
    @State
    public int page=1;
    public String pageFromInput="1";
    @StateEnd

    @Prop
    public PageData<Object> data=new PageData<>(new ArrayList<>());
    Function<Integer,Void> onChange=(Integer page)->{return null;};
    int maxVisiblePages=0;
    public Object callbackRender=null;
    @PropEnd


    public boolean nextDisabled=false;
    public boolean prevDisabled=true;

    public void setPage(int pageIn)
    {
        int lastPage=data.getTotal()/this.data.getRowsPerPage();
        if(pageIn>(lastPage))
        {
            return;
        }
        if(pageIn<1)
        {
            return;
        }
        this.stateKeySet("page",pageIn,
                "pageFromInput",pageIn+"");
        this.prevDisabled=pageIn==1;
        this.nextDisabled=pageIn==lastPage;
        this.data.setPage(pageIn);
        onChange.apply(pageIn);
    }

    public Function<Integer,Void> onPageChange=(Integer page) ->
    {
        this.setPage(page);
        this.data.setPage(page);
        return null;
    };

    public Function<Integer,Void> onRowsPerPageChange=(Integer rowsPerPage) ->
    {
        this.data.setRowsPerPage(rowsPerPage);
        return null;
    };

    public Function<Integer,Void> onClickNext=(Integer empty) ->
    {
        this.setPage(this.page+1);
        return null;
    };

    public Function<Integer,Void> onClickPrev=(Integer empty) ->
    {

        this.setPage(this.page-1);
        return null;
    };

    public Function<Object,Void> onClickGo=(Object event) ->
    {
        int number=Number.parseInt(this.pageFromInput);
        if(Number.isNaN(number)){ return null;}
        this.setPage(number);
        return null;
    };
}
