package org.dmcs.transaction.analytics.mesosphere.marathon;

public class UpgradeStrategy {

    private double minimumHealthCapacity;

    private double maximumOverCapacity;

    public UpgradeStrategy(double minimumHealthCapacity, double maximumOverCapacity) {
        this.minimumHealthCapacity = minimumHealthCapacity;
        this.maximumOverCapacity = maximumOverCapacity;
    }

    public double getMinimumHealthCapacity() {
        return minimumHealthCapacity;
    }

    public double getMaximumOverCapacity() {
        return maximumOverCapacity;
    }
}
