package org.dmcs.transaction.analytics.swarm.ports;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class PortMappingSerializer extends JsonSerializer<PortMapping> {

    @Override
    public void serialize(PortMapping portMapping, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        jsonGenerator.writeString(portMapping.getTargetPort() + ":" + portMapping.getSourcePort());
    }
}
