package ccbus.connect.core.ccbus.payload;

import ccbus.connect.ecma.File;
import ccbus.connect.core.ccbus.payload.FileData;
import ccbus.connect.core.ccbus.payload.FileUri;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Date;

public class FileUpload {
    public int __tag=-343;

    public ArrayList<FileData> files=new ArrayList<>();

    @JsonIgnore
    public ArrayList<File> __webFiles=new ArrayList<>();

    @JsonIgnore
    public ArrayList<FileUri> __uriFiles=new ArrayList<>();

    public FileUpload() {

    }

    public void addWebFiles(ArrayList<File> filesWeb)
    {
        for(int i=0;i<filesWeb.size();i++)
        {
            this.__webFiles.add(filesWeb.get(i));
        }

        FileData data;
        for(int i=0;i< filesWeb.size();i++)
        {
            data=new FileData();
            File f=filesWeb.get(i);
            data.origName=f.name;
            data.origSize=f.size;
            data.origDateLastModified=this.renderDate(f.lastModified);
            data.origType=f.type;
            files.add(data);
        }
    }

    public void setWebFiles(ArrayList<File> files)
    {
        this.clear();
        this.addWebFiles(files);
    }

    public void clear()
    {
        this.__webFiles=new ArrayList<>();
        this.files=new ArrayList<>();
        this.__uriFiles=new ArrayList<>();
    }

    public void addUriFile(String uri,String name)
    {
        addUriFile(uri,name,"application/octet-stream");
    }

    public void addUriFile(String uri,String name,String type)
    {
        __uriFiles.add(new FileUri(uri,name,type));
        FileData data;
        data=new FileData();
        data.origName=name;
        data.origType=type;
        data.origDateLastModified=renderDate(-1);
        files.add(data);
    }

    public void setUriFile(String uri,String name,String type)
    {
        this.clear();
        this.addUriFile(uri,name,type);
    }

    public void setUriFile(String uri,String name)
    {
        this.clear();
        this.addUriFile(uri,name);
    }

    public Date renderDate(int lastModified)
    {
        return null;
    }

    public ArrayList getSourceFiles()
    {
        return this.__webFiles.size()>0 ? this.__webFiles : this.__uriFiles;
    }

}
