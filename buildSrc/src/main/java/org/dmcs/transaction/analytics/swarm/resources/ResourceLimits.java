package org.dmcs.transaction.analytics.swarm.resources;

import org.dmcs.transaction.analytics.swarm.resources.Memory;

public class ResourceLimits {

    private String cpus;

    private Memory memory;

    public ResourceLimits(double cpus, Memory memory){
        this.cpus = Double.toString(cpus);
        this.memory = memory;
    }

    public String getCpus() {
        return cpus;
    }

    public Memory getMemory() {
        return memory;
    }
}
