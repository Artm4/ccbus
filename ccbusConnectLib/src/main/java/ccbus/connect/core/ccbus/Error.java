package ccbus.connect.core.ccbus;

import ccbus.connect.system.ExportModule;

@ExportModule
public class Error
{
    public String error="";
    public int errno=0;

    public Error()
    {

    }

    public Error(String error, int errno)
    {
        this.error = error;
        this.errno = errno;
    }

    public Error(String error)
    {
        this.error = error;
        this.errno = 1;
    }
}
