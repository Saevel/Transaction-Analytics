package org.dmcs.transaction.analytics.swarm.environment;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Variable implements  EnvironmentVariable{

    @JsonIgnore
    private String name;

    @JsonIgnore
    private String value;

    public Variable(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
