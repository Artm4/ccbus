package ccbus.demo.ccbus.worker.web;

import ccbus.connect.core.ccbus.WorkerServerImpl;

import ccbus.connect.core.ccbus.Error;
import ccbus.connect.core.ccbus.ExtComponent;
import ccbus.connect.core.ccbus.WorkerServer;
import ccbus.connect.core.ccbus.payload.FileUpload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import ccbus.connect.core.react.React;

public class Uploader_uploadFiles_0 extends WorkerServerImpl<Boolean>
{
    public 
    int val;
    public 
    FileUpload fileUpload;
    public  void compute() {
                int myVal=val;
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
                this.complete(true);
            }
    
}