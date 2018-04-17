package org.dmcs.transaction.analytics.swarm.ports;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = PortMappingSerializer.class)
@JsonDeserialize(using = PortMapppingDeserializer.class)
public class PortMapping {

    private int targetPort;

    private int sourcePort;

    public PortMapping(int targetPort, int sourcePort) {
        this.targetPort = targetPort;
        this.sourcePort = sourcePort;
    }

    public int getTargetPort() {
        return targetPort;
    }

    public int getSourcePort() {
        return sourcePort;
    }
}
