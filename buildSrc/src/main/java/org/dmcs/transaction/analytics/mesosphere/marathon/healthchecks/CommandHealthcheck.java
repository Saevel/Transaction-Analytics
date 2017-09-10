package org.dmcs.transaction.analytics.mesosphere.marathon.healthchecks;

/**
 * Created by kamil on 2017-09-10.
 */
public class CommandHealthcheck extends Healthcheck {

    private Command command;

    public CommandHealthcheck(HealthcheckProtocol protocol, long gracePeriodSeconds, long intervalSeconds, long timeoutSeconds, long maxConsecutiveFailures, Command command) {
        super(HealthcheckProtocol.COMMAND, gracePeriodSeconds, intervalSeconds, timeoutSeconds, maxConsecutiveFailures);
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
