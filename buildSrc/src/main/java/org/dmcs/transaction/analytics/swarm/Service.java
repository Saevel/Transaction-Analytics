package org.dmcs.transaction.analytics.swarm;

import org.dmcs.transaction.analytics.swarm.environment.EnvironmentVariable;
import org.dmcs.transaction.analytics.swarm.ports.PortMapping;

import java.util.Collection;
import java.util.Properties;

public class Service {

    private String image;

    private Deployment deploy;

    private Collection<PortMapping> ports;

    private Collection<String> networks;

    private Properties environment;

    public Service(String image, Deployment deploy, Collection<PortMapping> ports, Collection<String> networks, Properties environment) {
        this.image = image;
        this.deploy = deploy;
        this.ports = ports;
        this.networks = networks;
        this.environment = environment;
    }

    public String getImage() {
        return image;
    }

    public Deployment getDeploy() {
        return deploy;
    }

    public Collection<PortMapping> getPorts() {
        return ports;
    }

    public Collection<String> getNetworks() {
        return networks;
    }

    public Properties getEnvironment() {
        return environment;
    }
}
