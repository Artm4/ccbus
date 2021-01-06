package ccbus.connect.system;

import ccbus.connect.core.ccbus.Error;
import ccbus.connect.core.ccbus.payload.FileDownload;

public class ResponseT<T>
{
    public Error error=new Error();
    public T payload;

    private IResponseFileResource fileResource;

    public ResponseT(IResponseFileResource fileResource)
    {
        this.fileResource=fileResource;
    }

    public ResponseT()
    {

    }

    public void error(Error error)
    {
        this.error = error;
    }

    public void error(String error)
    {
        this.error = new Error(error);
    }

    public void payload(T workerPayload) throws Exception
    {
        if(null==workerPayload && (error.errno==0 && 0==error.error.length()))
        {
            throw new Exception("Error: complete not called on Worker neither error raised");
        }
        this.payload = workerPayload;
    }

    public Object payload()
    {
        return payload instanceof FileDownload ? fileResource.payload((FileDownload)payload) : this;
    }
}
