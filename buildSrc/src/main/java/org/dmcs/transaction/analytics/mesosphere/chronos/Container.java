package org.dmcs.transaction.analytics.mesosphere.chronos;

import org.dmcs.transaction.analytics.mesosphere.ContainerType;
import org.dmcs.transaction.analytics.mesosphere.NetworkType;

public class Container {

    private String image;

    private ContainerType type;

    private NetworkType network;

    public Container(String image, ContainerType type, NetworkType network) {
        this.image = image;
        this.type = type;
        this.network = network;
    }

    public String getImage() {
        return image;
    }

    public ContainerType getType() {
        return type;
    }

    public NetworkType getNetwork() {
        return network;
    }
}