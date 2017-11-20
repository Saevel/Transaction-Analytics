package org.dmcs.transaction.analytics.swarm;

import org.dmcs.transaction.analytics.swarm.resources.Resources;

public class Deployment {

    private int replicas;

    private Resources resources;

    private RestartPolicy restart_policy;

    public Deployment(int replicas, Resources resources, RestartPolicy restart_policy) {
        this.replicas = replicas;
        this.resources = resources;
        this.restart_policy = restart_policy;
    }

    public int getReplicas() {
        return replicas;
    }

    public Resources getResources() {
        return resources;
    }

    public RestartPolicy getRestart_policy() {
        return restart_policy;
    }
}