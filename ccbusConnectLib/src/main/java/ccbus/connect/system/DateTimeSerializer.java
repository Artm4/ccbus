package ccbus.connect.system;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.joda.time.DateTime;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class DateTimeSerializer extends StdSerializer<DateTime> {
    private SimpleDateFormat formatter
            = new SimpleDateFormat("dd-MM-yyyy'T'hh:mm:ssZ");

    public DateTimeSerializer() {
        this(null);
    }

    public DateTimeSerializer(Class t) {
        super(t);
    }

    @Override
    public void serialize (DateTime value, JsonGenerator gen, SerializerProvider arg2)
            throws IOException, JsonProcessingException {
        gen.writeString(formatter.format(value));
    }
}
