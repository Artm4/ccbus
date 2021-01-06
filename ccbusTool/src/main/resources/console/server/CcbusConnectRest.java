package %PACKAGE_NAME%;

import ccbus.connect.system.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/*")
public class CcbusConnectRest
{
    @RequestMapping("/ccbus")
    @CrossOrigin(origins = "http://localhost:3000")
    public Object connect(HttpServletRequest request)
    {
        %WORKER_HANDLER_PACKAGE_NAME%.WorkerHandler workerHandler=new %WORKER_HANDLER_PACKAGE_NAME%.WorkerHandler();
        return workerHandler.handle(request);
    }
}
