package ccbus.connect.core.ccbus.payload;

public class FileUri {
    public String uri;
    public String name;
    public String type;

    public FileUri()
    {

    }

    public FileUri(String uri, String name, String type)
    {
        this.uri = uri;
        this.name = name;
        this.type = type;
    }
}
