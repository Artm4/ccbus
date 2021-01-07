package ccbus.demo.ccbus.worker.web;

import ccbus.connect.core.ccbus.WorkerServerImpl;

import ccbus.connect.core.ccbus.Error;
import ccbus.connect.core.ccbus.ExtComponent;
import ccbus.connect.core.ccbus.WorkerServer;
import ccbus.connect.core.ccbus.lib.FileSaver;
import ccbus.connect.core.ccbus.payload.FileDownload;

import java.io.File;
import ccbus.connect.core.react.React;

public class Downloader_download_0 extends WorkerServerImpl<FileDownload>
{
    public  void compute() {
                String fileName="Screenshot_20200108.png";
                File file = new File(this.getClass().getClassLoader().getResource("public/pics/").getPath()+fileName);
                complete(new FileDownload(file));
            }
    
}