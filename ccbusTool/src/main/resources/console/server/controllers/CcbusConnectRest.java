import ccbus.connect.system.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/*")
public class CcbusConnectRest
{
    @RequestMapping("/ccbus")
    public Response connect(HttpServletRequest request)
    {
        com.edatachase.vehiclechecks.ccbus.WorkerHandler workerHandler=new com.edatachase.vehiclechecks.ccbus.WorkerHandler();
        return workerHandler.handle(request);
    }
}
