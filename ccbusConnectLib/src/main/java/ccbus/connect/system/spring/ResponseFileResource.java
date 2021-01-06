package ccbus.connect.system.spring;

import ccbus.connect.core.ccbus.payload.FileDownload;
import ccbus.connect.system.IResponseFileResource;
import org.springframework.core.io.InputStreamResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ResponseFileResource implements IResponseFileResource
{
    public ResponseFileResource()
    {

    }

    public Object payload(FileDownload fileDownload)
    {
        String fileName=fileDownload.fileName;
        MediaType mediaType = MediaType.parseMediaType(fileDownload.mediaType);
        InputStreamResource  resource = new InputStreamResource(fileDownload.fileInputStream);

        return ResponseEntity.ok()
                // Content-Disposition addHeader("Access-Control-Expose-Headers", "Content-Disposition");
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,"Content-Disposition")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                // Content-Type
                .contentType(mediaType)
                // Contet-Length
                .contentLength(fileDownload.length) //
                .body(resource);
    }
}
