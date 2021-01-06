package ccbus.connect.system;

import ccbus.connect.core.ccbus.payload.FileDownload;

public interface IResponseFileResource {
    public Object payload(FileDownload fileDownload);
}
