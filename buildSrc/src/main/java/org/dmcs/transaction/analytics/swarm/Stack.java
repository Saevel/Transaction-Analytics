package org.dmcs.transaction.analytics.swarm;

import org.dmcs.transaction.analytics.swarm.networks.NetworkDefinition;

import java.util.Map;

public class Stack {

    private String version;

    private Map<String, Service> services;

    private Map<String, NetworkDefinition> networks;

    public Stack(String version, Map<String, Service> services, Map<String, NetworkDefinition> networks) {
        this.version = version;
        this.services = services;
        this.networks = networks;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, Service> getServices() {
        return services;
    }

    public Map<String, NetworkDefinition> getNetworks() {
        return networks;
    }
}
