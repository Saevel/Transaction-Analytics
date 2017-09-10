package org.dmcs.transaction.analytics.mesosphere.marathon.healthchecks;

/**
 * Created by kamil on 2017-09-10.
 */
public class HttpHealthcheck extends Healthcheck {

    private int port;

    private String path;

    public HttpHealthcheck(long gracePeriodSeconds, long intervalSeconds, long timeoutSeconds, long maxConsecutiveFailures, int port, String path) {
        super(HealthcheckProtocol.HTTP, gracePeriodSeconds, intervalSeconds, timeoutSeconds, maxConsecutiveFailures);
        this.port = port;
        this.path = path;
    }

    public int getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }
}
