package ccbus.connect.core.ccbus.payload;

import java.io.*;
import java.nio.file.Files;

public class FileDownload {
    public FileInputStream fileInputStream;
    public InputStream inputStream;
    public String fileName;
    public String mediaType;
    public long length;

    public FileDownload(FileInputStream fileInputStream, String fileName, String mediaType,long length) {
        this.fileInputStream = fileInputStream;
        this.fileName = fileName;
        this.mediaType = mediaType;
        this.length = length;
        this.inputStream=fileInputStream;
    }

    public FileDownload(InputStream inputStream, String fileName, String mediaType, long length) {
        this.inputStream = inputStream;
        this.fileName = fileName;
        this.mediaType = mediaType;
        this.length = length;
    }

    public FileDownload(File file)
    {
        try {
            this.fileInputStream=new FileInputStream(file);
            this.inputStream=fileInputStream;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.fileName=file.getName();
        this.length=file.length();
        try {
            this.mediaType= Files.probeContentType(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
