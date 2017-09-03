package org.dmcs.transaction.analytics.mesosphere.chronos;

public class ChronosJob {

    private String name;

    private String command;

    private String schedule;

    private String cpus;

    private String mem;

    private Container container;

    public ChronosJob(String name, String command, String schedule, String cpus, String mem, Container container) {
        this.name = name;
        this.command = command;
        this.schedule = schedule;
        this.cpus = cpus;
        this.mem = mem;
        this.container = container;
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }

    public String getSchedule() {
        return schedule;
    }

    public String getCpus() {
        return cpus;
    }

    public String getMem() {
        return mem;
    }

    public Container getContainer() {
        return container;
    }
}
