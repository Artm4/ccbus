import ccbus.connect.system.Request;
import ccbus.connect.system.Response;
import ccbus.connect.core.ccbus.WorkerServer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import javax.servlet.http.HttpServletRequest;

public class WorkerHandler
{
    public Response handle(HttpServletRequest request)
    {
        Response response = new Response();
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JodaModule());
            Request requestObject = mapper.readValue(request.getReader(), Request.class);

            String packageName=this.getClass().getCanonicalName().replaceFirst(
                    this.getClass().getSimpleName(),"");

            String className = packageName+"worker.web."+requestObject.endpoint();

            Class<?> classRepr = Class.forName(className);

            Object userUpdate = mapper.convertValue(requestObject.payload, classRepr);

            ((WorkerServer)userUpdate).compute();
            response.payload(((WorkerServer)userUpdate).result());
            response.error(((WorkerServer)userUpdate).error());
        }
        catch (Exception e)
        {
            // Should log exception and put friendly message "Contact support, bla, bla ,bla..."
            response.error(e.getMessage());
        }

        return response;
    }
}
