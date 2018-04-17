package org.dmcs.transaction.analytics.swarm.resources;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class MemorySerializer extends JsonSerializer<Memory> {
    @Override
    public void serialize(Memory memory, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeString(memory.getValue() + "" + memory.getUnit().getValue());
    }
}
