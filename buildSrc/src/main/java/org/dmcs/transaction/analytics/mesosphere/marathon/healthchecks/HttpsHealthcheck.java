package org.dmcs.transaction.analytics.mesosphere.marathon.healthchecks;

/**
 * Created by kamil on 2017-09-10.
 */
public class HttpsHealthcheck extends Healthcheck {

    private int port;

    private String path;

    public HttpsHealthcheck(long gracePeriodSeconds, long intervalSeconds, long timeoutSeconds, long maxConsecutiveFailures, int port, String path) {
        super(HealthcheckProtocol.HTTPS, gracePeriodSeconds, intervalSeconds, timeoutSeconds, maxConsecutiveFailures);
        this.port = port;
        this.path = path;
    }
}
