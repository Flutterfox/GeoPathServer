package trenton.fox;
import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class CustomDateSerializer extends JsonSerializer<Date>{

	CustomDateSerializer(){};
	
	@Override
	public void serialize(Date date, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
	    jgen.writeNumber(date.getTime());
	}
}