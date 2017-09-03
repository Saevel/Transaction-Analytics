package org.dmcs.transaction.analytics.mesosphere.marathon;

import java.util.Map;

/**
 * { "port": 8080, "protocol": "tcp", "name": "http", "labels": { "VIP_0": "10.0.0.1:80" } }
 */
public class PortDefinition {

    private int port;

    private String protocol;

    private String name;

    private Map<String, String> labels;

    public PortDefinition(int port, String protocol, String name, Map<String, String> labels) {
        this.port = port;
        this.protocol = protocol;
        this.name = name;
        this.labels = labels;
    }

    public int getPort() {
        return port;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getLabels() {
        return labels;
    }
}
