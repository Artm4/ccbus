package %PACKAGE_NAME%;

import ccbus.connect.system.Request;
import ccbus.connect.system.Response;

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
}
