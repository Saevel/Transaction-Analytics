package org.dmcs.transaction.analytics.mesosphere.marathon.healthchecks;

/**
 * Created by kamil on 2017-09-10.
 */
public class Command {

    private String value;

    public Command(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
