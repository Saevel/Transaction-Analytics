package org.dmcs.transaction.analytics.swarm.networks;

public class NetworkDefinition {

    private NetworkDriver driver;

    public NetworkDefinition(NetworkDriver driver) {
        this.driver = driver;
    }

    public NetworkDriver getDriver() {
        return driver;
    }
}
