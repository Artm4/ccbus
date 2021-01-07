package ccbus.demo.ccbus.service;

import ccbus.connect.core.ccbus.WorkerService;
import ccbus.connect.core.ccbus.payload.FileDownload;
import ccbus.connect.core.ccbus.payload.FileUpload;
import ccbus.demo.ccbus.payload.SomePayload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.BiFunction;


public class ServiceSome extends WorkerService<SomePayload>
{
    public String someVal="Hello";
    private int ival=1;
    public FileUpload fileUpload=new FileUpload();

    @Override
    public void compute()
    {
        int size=fileUpload.files.size();
        int a=2;
        int b=3;
        ival=2+3;
        String val=someVal;

        try {
            for(int i=0;i<fileUpload.files.size();i++)
            {
                String name=fileUpload.files.get(i).origName;
                Files.copy(fileUpload.files.get(i).getPart().getInputStream(),
                        Paths.get(this.getClass().getClassLoader().getResource("public/pics").getPath() + "/" + name));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.complete(new SomePayload(1,"name"));
    }

    public void setSomeVal(String someVal)
    {
        this.someVal = someVal;
    }
}
