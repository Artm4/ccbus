package ccbus.connect.system;

import ccbus.connect.core.ccbus.payload.FileData;
import ccbus.connect.core.ccbus.payload.FileUpload;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class FileDataDeserialize extends StdDeserializer<FileData>
{
    ArrayList<Part> parts=new ArrayList<>();

    public FileDataDeserialize()
    {
        this((Class<?>) null);
    }

    public FileDataDeserialize(HttpServletRequest request)
    {
        super((Class<?>) null);
        try
        {
            if(request.getContentType().startsWith("multipart"))
            {
                this.parts = new ArrayList<>(request.getParts());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ServletException e)
        {
            e.printStackTrace();
        }
    }


    public FileDataDeserialize(Class<?> vc)
    {
        super(vc);
    }

    @Override
    public FileData deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException
    {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        FileData fileData=new FileData();
        fileData.origName = node.get("origName").asText();
        fileData.origSize = (Long) node.get("origSize").longValue();
        fileData.origType = node.get("origType").asText();

        try {
            String dateEncoded=node.get("origDateLastModified").asText();
            try {
                long seconds = Long.parseLong(dateEncoded);
                fileData.origDateLastModified = new Date(seconds);
            } catch (NumberFormatException nfe) {
                fileData.origDateLastModified = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(node.get("origDateLastModified").asText());
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        fileData.index = (Integer)node.get("index").numberValue();
        if(fileData.index<parts.size())
        {
            fileData.part = parts.get(fileData.index);
        }

        return fileData;
    }

}