package org.dmcs.transaction.analytics.mesosphere.marathon;

public class PortMapping {

    private int containerPort;

    private int hostPort;

    private int servicePort;

    private String protocol;

    public PortMapping(int containerPort, int hostPort, int servicePort, String protocol) {
        this.containerPort = containerPort;
        this.hostPort = hostPort;
        this.servicePort = servicePort;
        this.protocol = protocol;
    }

    public int getContainerPort() {
        return containerPort;
    }

    public int getHostPort() {
        return hostPort;
    }

    public int getServicePort() {
        return servicePort;
    }

    public String getProtocol() {
        return protocol;
    }
}
