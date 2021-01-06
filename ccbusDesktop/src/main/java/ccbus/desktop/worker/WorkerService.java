package ccbus.desktop.worker;


import ccbus.connect.core.ccbus.payload.FileData;
import ccbus.connect.core.ccbus.payload.FileDownload;
import ccbus.connect.core.ccbus.payload.FileUpload;
import ccbus.connect.core.ccbus.payload.FileUri;
import ccbus.connect.system.ResponseT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.util.ArrayList;

import ccbus.connect.system.spring.ResponseFileResource;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.joda.time.DateTime;

public class WorkerService<T>  implements IWorkerService <T>
{
    private Worker worker =new Worker();
    HttpClient client;
    static String uri="http://localhost:8080/api/ccbus/";

    Request payload;

    Class<T> payloadClass;

    String responseType="";

    public void setPayload(Object payload,Class<T> payloadClass,String endpoint)
    {
        this.payloadClass=payloadClass;
        this.payload = new Request("worker."+endpoint,payload);
    }

    public void setPayload(Object payload,Class<T> payloadClass,String endpoint, String responseType)
    {
        setPayload(payload,payloadClass,endpoint);
        this.responseType=responseType;
    }

    public WorkerService()
    {
        client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    public int publishBody(HttpRequest.Builder builder)
    {
        HttpRequest.BodyPublisher result;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        ObjectWriter ow = mapper.writer();//.withDefaultPrettyPrinter()
        String data="";
        boolean multiData=false;

        MultiPartBodyPublisher publisher = new MultiPartBodyPublisher();

        int indexFiles=0;
        for(Field field: this.payload.payload.getClass().getDeclaredFields())
        {
            if(field.getGenericType().getTypeName().contains("ccbus.payload.FileUpload"))
            {
                try {
                    field.setAccessible(true);
                    FileUpload fileUpload=(FileUpload) field.get(this.payload.payload);
                    for(int j=0;j<fileUpload.__uriFiles.size();j++)
                    {
                        Path filePath=Paths.get(fileUpload.__uriFiles.get(j).uri);
                        publisher.addPart("file", filePath);
                        FileData fileData=fileUpload.files.get(j);
                        fileData.index = indexFiles;

                        try {
                            BasicFileAttributes attributes = Files.readAttributes(filePath, BasicFileAttributes.class);
                            fileData.origDateLastModified= new DateTime(attributes.lastModifiedTime().toMillis()).toDate();
                        } catch (Exception e) {
                            fileData.origDateLastModified= DateTime.now().toDate();
                        }

                        try {
                            fileData.origType=Files.probeContentType(filePath);
                            fileData.origSize=Files.size(filePath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        indexFiles++;
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                multiData=true;
            }
        }

        if(multiData)
        {
            try {
                publisher.addPart("request", ow.writeValueAsString(payload));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            builder.POST(publisher.build());
            builder.header("Content-Type", "multipart/form-data; boundary=" + publisher.getBoundary());

            return 1;
        }

        try
        {
            data = ow.writeValueAsString(payload);
            builder.POST(HttpRequest.BodyPublishers.ofString(data));
            builder.header("Content-Type", "application/json");
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    public void compute(WorkerCallback<T> callback)
    {
        WorkerCompute<T> compute=new WorkerCompute<>()
        {
            @Override
            public ResponseT<T>  compute()
            {
                String data="";

                ObjectMapper mapper = new ObjectMapper();
                ObjectWriter ow = mapper.writer();//.withDefaultPrettyPrinter()
                try
                {
                    data = ow.writeValueAsString(payload);
                } catch (JsonProcessingException e)
                {
                    e.printStackTrace();
                }

//                HttpRequest request = HttpRequest.newBuilder()
//                        .uri(URI.create(uri))
//                        .timeout(Duration.ofMinutes(1))
//                        .header("Content-Type", "application/json")
//                        .POST(HttpRequest.BodyPublishers.ofString(data))
//                        .build();

                HttpRequest.Builder builder=  HttpRequest.newBuilder()
                        .uri(URI.create(uri))
                        .timeout(Duration.ofMinutes(1));

                      //  .header("Content-Type", "application/json");

                publishBody(builder);
                HttpRequest request = builder.build();

                        HttpResponse<InputStream> response =
                        null;
                try
                {
                    //response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
                } catch (IOException e)
                {
                    e.printStackTrace();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                //System.out.println(response.statusCode());
                //System.out.println(response.body());

                if(response.headers().firstValue("Content-Disposition").isEmpty()) {

                    ResponseT<T> responseObject = new ResponseT(new ResponseFileResource());
                    try {
                        responseObject = mapper.readValue(response.body(), ResponseT.class);

                        responseObject.payload = mapper.convertValue(responseObject.payload, payloadClass);
                        return responseObject;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                    // T should be FileDownload
                    ResponseT<T> responseObject = new ResponseT(new ResponseFileResource());
                    responseObject.payload=(T)responseBlob(response);
                    return responseObject;
                }
                return null;
            }
        };

        worker.compute(
                callback,compute
        );
    }


    FileDownload responseBlob(HttpResponse<InputStream> response)
    {
        String fileName = "";
        String disposition = response.headers().firstValue("content-Disposition").get();
        String contentType = response.headers().firstValue("content-type").get();
        int contentLength = Integer.valueOf(response.headers().firstValue("content-length").get());

        int index = disposition.indexOf("filename=");
        if (index > 0) {
            fileName = disposition.substring(index + 10,
                    disposition.length() - 1);
        }


        FileDownload fileDownload=new FileDownload(response.body(), fileName, contentType, contentLength);
        return fileDownload;
    }

}
