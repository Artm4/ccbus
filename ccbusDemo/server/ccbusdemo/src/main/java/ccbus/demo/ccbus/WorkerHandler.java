package ccbus.demo.ccbus;

import ccbus.connect.core.ccbus.Error;
import ccbus.connect.system.Request;
import ccbus.connect.system.Response;
import ccbus.connect.core.ccbus.WorkerServerImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

public class WorkerHandler extends ccbus.connect.system.WorkerHandler
{
    public Object handle(HttpServletRequest request)
    {
        String packageName=this.getClass().getCanonicalName().replaceFirst(
                this.getClass().getSimpleName(),"");
        this.setPackageName(packageName);
        return super.handle(request);
    }

    public boolean preComplete(HttpServletRequest request,Request requestObject,Response response)
    {
        return this.secure(request,requestObject,response);
    }

    private boolean secure(HttpServletRequest request,Request requestObject,Response response)
    {


        return true;
    }

}