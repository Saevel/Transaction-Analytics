package org.dmcs.transaction.analytics.mesosphere.marathon;

import org.dmcs.transaction.analytics.mesosphere.ContainerType;
import org.dmcs.transaction.analytics.mesosphere.PersistentVolume;

import java.util.Collection;

public abstract class Container {

    protected ContainerType type;

    protected Collection<PersistentVolume> volumes;

    protected Container(ContainerType type, Collection<PersistentVolume> volumes) {
        this.type = type;
        this.volumes = volumes;
    }

    public ContainerType getType() {
        return type;
    }

    public Collection<PersistentVolume> getVolumes() {
        return volumes;
    }
}
