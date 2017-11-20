package org.dmcs.transaction.analytics.swarm.environment;

public class UndefinedVariable implements EnvironmentVariable {

    private String name;

    public UndefinedVariable(String name) {
        this.name = name;
    }

    @Override
    public String getValue() {
        return name;
    }
}
