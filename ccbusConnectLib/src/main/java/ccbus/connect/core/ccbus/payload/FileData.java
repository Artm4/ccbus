package ccbus.connect.core.ccbus.payload;

import javax.servlet.http.Part;
import java.util.Date;

public class FileData {
    public int index=-1;
    public String origName;
    public long origSize=-1;
    public Date origDateLastModified;
    public String origType="";

    public Part part;

    public int getIndex() {
        return index;
    }

    public Part getPart() {
        return part;
    }

    public String getOrigName() {
        return origName;
    }

    public long getOrigSize() {
        return origSize;
    }

    public Date getOrigDateLastModified() {
        return origDateLastModified;
    }

    public String getOrigType() {
        return origType;
    }
}
