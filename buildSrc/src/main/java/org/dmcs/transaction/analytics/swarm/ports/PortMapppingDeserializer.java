package org.dmcs.transaction.analytics.swarm.ports;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class PortMapppingDeserializer extends JsonDeserializer<PortMapping> {
    @Override
    public PortMapping deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String[] parts = jsonParser.getText().split(":");
        return new PortMapping(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }
}
