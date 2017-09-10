package org.dmcs.transaction.analytics.mesosphere.marathon;


import org.dmcs.transaction.analytics.mesosphere.marathon.containers.Container;
import org.dmcs.transaction.analytics.mesosphere.marathon.healthchecks.Healthcheck;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MarathonApplication {

    // TODO: DOX All!

    // TODO: Consider a builder for this?

    private String id;

    private String cmd;

    private String cpus;

    private String mem;

    private Collection<PortDefinition> portDefinitions;

    private boolean requirePorts;

    private int instances;

    private String executor;

    private Container container;

    private Map<String, String> env;

    private List<String> constraints;

    private List<String> acceptedResourceRoles;

    private Map<String, String> labels;

    private List<String> dependencies;

    private List<Healthcheck> healthchecks;

    private long backoffSeconds;

    private double backoffFactor;

    private int taskKillGracePeriodSeconds;

    private UpgradeStrategy upgradeStrategy;

    private IpAddress ipAddress;

    public MarathonApplication(String id, String cmd, String cpus, String mem, Collection<PortDefinition> portDefinitions, boolean requirePorts, int instances, String executor, Container container, Map<String, String> env, List<String> constraints, List<String> acceptedResourceRoles, Map<String, String> labels, List<String> dependencies, List<Healthcheck> healthchecks, long backoffSeconds, double backoffFactor, int taskKillGracePeriodSeconds, UpgradeStrategy upgradeStrategy, IpAddress ipAddress) {
        this.id = id;
        this.cmd = cmd;
        this.cpus = cpus;
        this.mem = mem;
        this.portDefinitions = portDefinitions;
        this.requirePorts = requirePorts;
        this.instances = instances;
        this.executor = executor;
        this.container = container;
        this.env = env;
        this.constraints = constraints;
        this.acceptedResourceRoles = acceptedResourceRoles;
        this.labels = labels;
        this.dependencies = dependencies;
        this.healthchecks = healthchecks;
        this.backoffSeconds = backoffSeconds;
        this.backoffFactor = backoffFactor;
        this.taskKillGracePeriodSeconds = taskKillGracePeriodSeconds;
        this.upgradeStrategy = upgradeStrategy;
        this.ipAddress = ipAddress;
    }

    public String getId() {
        return id;
    }

    public String getCmd() {
        return cmd;
    }

    public String getCpus() {
        return cpus;
    }

    public String getMem() {
        return mem;
    }

    public Collection<PortDefinition> getPortDefinitions() {
        return portDefinitions;
    }

    public boolean isRequirePorts() {
        return requirePorts;
    }

    public int getInstances() {
        return instances;
    }

    public String getExecutor() {
        return executor;
    }

    public Container getContainer() {
        return container;
    }

    public Map<String, String> getEnv() {
        return env;
    }

    public List<String> getConstraints() {
        return constraints;
    }

    public List<String> getAcceptedResourceRoles() {
        return acceptedResourceRoles;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public List<String> getDependencies() {
        return dependencies;
    }

    public List<Healthcheck> getHealthchecks() {
        return healthchecks;
    }

    public long getBackoffSeconds() {
        return backoffSeconds;
    }

    public double getBackoffFactor() {
        return backoffFactor;
    }

    public int getTaskKillGracePeriodSeconds() {
        return taskKillGracePeriodSeconds;
    }

    public UpgradeStrategy getUpgradeStrategy() {
        return upgradeStrategy;
    }

    public IpAddress getIpAddress() {
        return ipAddress;
    }
}