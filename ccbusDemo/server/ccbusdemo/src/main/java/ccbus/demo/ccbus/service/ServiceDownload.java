package ccbus.demo.ccbus.service;

import ccbus.connect.core.ccbus.WorkerService;
import ccbus.connect.core.ccbus.payload.FileDownload;
import ccbus.connect.core.ccbus.payload.FileUpload;
import ccbus.demo.ccbus.payload.SomePayload;

import java.io.File;


public class ServiceDownload extends WorkerService<FileDownload>
{
    int abs;
    @Override
    public void compute()
    {
        abs=1;
        String fileName="Screenshot_20200108.png";
        File file = new File(this.getClass().getClassLoader().getResource("public/pics/").getPath()+fileName);
        complete(new FileDownload(file));
    }
}
