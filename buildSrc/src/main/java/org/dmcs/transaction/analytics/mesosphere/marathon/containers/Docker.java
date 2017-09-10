package org.dmcs.transaction.analytics.mesosphere.marathon.containers;

import org.dmcs.transaction.analytics.mesosphere.NetworkType;
import org.dmcs.transaction.analytics.mesosphere.Parameter;
import org.dmcs.transaction.analytics.mesosphere.marathon.PortMapping;

import java.util.Collection;

public class Docker {

    private String image;

    private NetworkType network;

    private Collection<PortMapping> portMappings;

    private boolean privileged;

    private Collection<Parameter> parameters;

    public Docker(String image, NetworkType network, Collection<PortMapping> portMappings, boolean privileged, Collection<Parameter> parameters) {
        this.image = image;
        this.network = network;
        this.portMappings = portMappings;
        this.privileged = privileged;
        this.parameters = parameters;
    }

    public String getImage() {
        return image;
    }

    public NetworkType getNetwork() {
        return network;
    }

    public Collection<PortMapping> getPortMappings() {
        return portMappings;
    }

    public boolean isPrivileged() {
        return privileged;
    }

    public Collection<Parameter> getParameters() {
        return parameters;
    }
}
