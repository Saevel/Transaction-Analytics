package org.dmcs.transaction.analytics.mesosphere.marathon;


import org.dmcs.transaction.analytics.mesosphere.ContainerType;
import org.dmcs.transaction.analytics.mesosphere.PersistentVolume;

import java.util.Collection;

public class DockerContainer extends Container {

    private Docker docker;

    public DockerContainer(ContainerType type, Collection<PersistentVolume> volumes, Docker docker) {
        super(ContainerType.DOCKER, volumes);
        this.docker = docker;
    }

    public Docker getDocker() {
        return docker;
    }
}
