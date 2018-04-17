package org.dmcs.transaction.analytics.swarm.resources;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class MemoryDeserializer extends JsonDeserializer<Memory> {
    @Override
    public Memory deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String text = jsonParser.getText();
        if(text.contains("M")){
            int value = Integer.parseInt(text.split("M")[0]);
            return new Memory(value, MemoryUnit.Megabytes);
        } else {
            return null;
        }
    }
}
