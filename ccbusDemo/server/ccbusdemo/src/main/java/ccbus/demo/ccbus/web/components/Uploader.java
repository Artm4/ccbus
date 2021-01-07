package ccbus.demo.ccbus.web.components;

import ccbus.connect.core.ccbus.Error;
import ccbus.connect.core.ccbus.ExtComponent;
import ccbus.connect.core.ccbus.WorkerServer;
import ccbus.connect.core.ccbus.payload.FileUpload;
import ccbus.connect.ecma.Event;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Uploader extends ExtComponent {
    FileUpload fileUpload=new FileUpload();
    int val=1;

    void onChange(Event e)
    {
        fileUpload.addWebFiles(e.target.files);
        if(fileUpload.files.size()>2) {
            uploadFiles();
        }
    }

    void uploadFiles()
    {
        new WorkerServer<Boolean>()
        {
            @Override
            public void compute() {
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

            @Override
            public void onCompletion(Boolean aBoolean) {
                fileUpload.clear();
            }

            @Override
            public void onError(Error error) {

            }
        };
    }
}
