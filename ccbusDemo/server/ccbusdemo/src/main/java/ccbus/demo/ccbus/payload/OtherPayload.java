package ccbus.demo.ccbus.payload;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ccbus.demo.ccbus.payload.SomePayload;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
public class OtherPayload extends SomePayload
{
    public int a;
    public String name;

    public OtherPayload()
    {
    }

    public OtherPayload(int a, String name)
    {
        this.a = a;
        this.name = name;
    }
}
