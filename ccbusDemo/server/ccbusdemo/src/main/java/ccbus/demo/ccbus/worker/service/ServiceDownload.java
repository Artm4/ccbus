package ccbus.demo.ccbus.worker.service;

import ccbus.connect.core.ccbus.WorkerServerImpl;

import ccbus.connect.core.ccbus.WorkerService;
import ccbus.connect.core.ccbus.payload.FileDownload;
import ccbus.connect.core.ccbus.payload.FileUpload;
import ccbus.demo.ccbus.payload.SomePayload;

import java.io.File;

public class ServiceDownload extends WorkerServerImpl<FileDownload>
{
    public  void compute()
    {

        String fileName="Screenshot_20200108.png";
        File file = new File(this.getClass().getClassLoader().getResource("public/pics/").getPath()+fileName);
        this.complete(new FileDownload(file));
    }
    
}