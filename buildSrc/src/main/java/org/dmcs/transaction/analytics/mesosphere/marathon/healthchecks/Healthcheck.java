package org.dmcs.transaction.analytics.mesosphere.marathon.healthchecks;

/**
 * Created by kamil on 2017-09-10.
 */
public abstract class Healthcheck {

    private HealthcheckProtocol protocol;

    private long gracePeriodSeconds;

    private long intervalSeconds;

    private long timeoutSeconds;

    private long maxConsecutiveFailures;

    public Healthcheck(HealthcheckProtocol protocol, long gracePeriodSeconds, long intervalSeconds, long timeoutSeconds, long maxConsecutiveFailures) {
        this.protocol = protocol;
        this.gracePeriodSeconds = gracePeriodSeconds;
        this.intervalSeconds = intervalSeconds;
        this.timeoutSeconds = timeoutSeconds;
        this.maxConsecutiveFailures = maxConsecutiveFailures;
    }

    public HealthcheckProtocol getProtocol() {
        return protocol;
    }

    public long getGracePeriodSeconds() {
        return gracePeriodSeconds;
    }

    public long getIntervalSeconds() {
        return intervalSeconds;
    }

    public long getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public long getMaxConsecutiveFailures() {
        return maxConsecutiveFailures;
    }
}
