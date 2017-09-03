package org.dmcs.transaction.analytics.mesosphere;

public class PersistentVolume {

    private String containerPath;

    private String hostPath;

    private AccessMode mode;

    public PersistentVolume(String containerPath, String hostPath, AccessMode mode) {
        this.containerPath = containerPath;
        this.hostPath = hostPath;
        this.mode = mode;
    }

    public String getContainerPath() {
        return containerPath;
    }

    public String getHostPath() {
        return hostPath;
    }

    public AccessMode getMode() {
        return mode;
    }
}
