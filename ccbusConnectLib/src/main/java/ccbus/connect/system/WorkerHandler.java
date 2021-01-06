package ccbus.connect.system;

import ccbus.connect.core.ccbus.Error;
import ccbus.connect.core.ccbus.WorkerServerImpl;
import ccbus.connect.core.ccbus.payload.FileData;
import ccbus.connect.system.spring.ResponseFileResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class WorkerHandler
{
    public String packageName;
    public Object handle(HttpServletRequest request)
    {
        Response response = new Response(new ResponseFileResource());
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JodaModule());

            SimpleModule module = new SimpleModule();
            module.addDeserializer(FileData.class, new FileDataDeserialize(request));

            mapper.registerModule(module);
            //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            Request requestObject = Request.createRequestObject(request);

            if(!this.preComplete(request,requestObject,response)){return response.payload();}

            String packageName=this.getClass().getCanonicalName().replaceFirst(
                    this.getClass().getSimpleName(),"");

            String className = this.packageName+requestObject.endpoint();

            Class<?> classRepr = Class.forName(className);

            Object userUpdate = mapper.convertValue(requestObject.payload, classRepr);

            ((WorkerServerImpl)userUpdate).compute();
            response.error(((WorkerServerImpl)userUpdate).error());
            response.payload(((WorkerServerImpl)userUpdate).result());

            this.postComplete(request,requestObject,response);
        }
        catch (Exception e)
        {
            // Should log exception and put friendly message "Contact support, bla, bla ,bla..."
            response.error(e.getMessage());
            System.err.println(e.getMessage());
            e.printStackTrace(System.err);
        }
        return response.payload();
    }

    public void setPackageName(String packageName)
    {
        this.packageName = packageName;
    }

    public String getPackageName()
    {
        return packageName;
    }

    /**
     * return false to send back response after call
     * @param request
     * @param requestObject
     * @param response
     * @return
     */
    public boolean preComplete(HttpServletRequest request,Request requestObject,Response response)
    {
        return true;
    }

    public boolean postComplete(HttpServletRequest request,Request requestObject,Response response)
    {
        return true;
    }
}
