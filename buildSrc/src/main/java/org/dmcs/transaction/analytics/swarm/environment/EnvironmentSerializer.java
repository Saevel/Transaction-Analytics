package org.dmcs.transaction.analytics.swarm.environment;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class EnvironmentSerializer extends JsonSerializer<EnvironmentVariable> {
    @Override
    public void serialize(EnvironmentVariable environmentVariable, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeString(environmentVariable.getValue());
    }
}
