package ccbus.demo.ccbus.web.components;

import ccbus.connect.core.ccbus.Error;
import ccbus.connect.core.ccbus.ExtComponent;
import ccbus.connect.core.ccbus.WorkerServer;
import ccbus.connect.core.ccbus.lib.FileSaver;
import ccbus.connect.core.ccbus.payload.FileDownload;
import ccbus.connect.ecma.Event;

import java.io.File;

public class Downloader extends ExtComponent
{

    void onClick(Event e)
    {
        download();
    }

    void download()
    {
        new WorkerServer<FileDownload>()
        {
            @Override
            public void compute() {
                String fileName="Screenshot_20200108.png";
                File file = new File(this.getClass().getClassLoader().getResource("public/pics/").getPath()+fileName);
                complete(new FileDownload(file));
            }

            @Override
            public void onCompletion(FileDownload fileDownload)
            {
                FileSaver.saveAs(fileDownload.fileInputStream,fileDownload.fileName);
            }

            @Override
            public void onError(Error error) {

            }
        };
    }
}
