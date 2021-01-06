package ccbus.connect.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import javax.servlet.http.HttpServletRequest;

public class Request
{
    public String endpoint;
    public Object payload;

    public String endpoint()
    {
        return endpoint;
    }

    public Object payload()
    {
        return payload;
    }

    public static Request createRequestObject(HttpServletRequest request) throws Exception
    {
        Request requestObject=new Request();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());

        if(request.getContentType().startsWith("multipart/form-data"))
        {
            requestObject=mapper.readValue(request.getParameter("request"), Request.class);
        }
        else
        if(request.getContentType().startsWith("application/json"))// Json
        {
            requestObject=mapper.readValue(request.getReader(), Request.class);
        }
        else
        {
            throw new Exception("Not supported request type: "+request.getContentType());
        }
        return requestObject;
    }
}
