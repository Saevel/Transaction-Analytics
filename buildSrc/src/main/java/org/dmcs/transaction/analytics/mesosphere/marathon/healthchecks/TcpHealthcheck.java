package org.dmcs.transaction.analytics.mesosphere.marathon.healthchecks;

/**
 * Created by kamil on 2017-09-10.
 */
public class TcpHealthcheck extends Healthcheck {

    private int port;

    public TcpHealthcheck(HealthcheckProtocol protocol, long gracePeriodSeconds, long intervalSeconds, long timeoutSeconds, long maxConsecutiveFailures, int port) {
        super(HealthcheckProtocol.TCP, gracePeriodSeconds, intervalSeconds, timeoutSeconds, maxConsecutiveFailures);
        this.port = port;
    }

    public int getPort() {
        return port;
    }
}
