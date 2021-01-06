package ccbus.desktop.worker;

public class Request
{
    public String endpoint;
    public Object payload;

    public Request(String endpoint, Object payload)
    {
        this.endpoint = endpoint;
        this.payload = payload;
    }
}
