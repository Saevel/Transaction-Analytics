package org.dmcs.transaction.analytics.swarm.resources;

public class Resources {

    private ResourceLimits limits;

    public Resources(ResourceLimits limits) {
        this.limits = limits;
    }

    public ResourceLimits getLimits() {
        return limits;
    }
}
