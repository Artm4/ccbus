package ccbus.demo.controllers;

import ccbus.connect.system.Response;
import ccbus.demo.ccbus.WorkerHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("api/*")
public class CcbusConnectRest
{
    @CrossOrigin(origins = {"http://192.168.1.6:3000","http://localhost:3000"})
    @RequestMapping("/ccbus")
    public Object connect(HttpServletRequest request)
    {

        WorkerHandler workerHandler = new WorkerHandler();
        return workerHandler.handle(request);

    }


}
