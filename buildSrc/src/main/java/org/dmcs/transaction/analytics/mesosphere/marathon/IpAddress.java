package org.dmcs.transaction.analytics.mesosphere.marathon;

import java.util.List;
import java.util.Map;

/**
 * Created by kamil on 2017-09-10.
 */
public class IpAddress {

    private List<String> groups;

    private Map<String, String> labels;

    private String networkName;

    public IpAddress(List<String> groups, Map<String, String> labels, String networkName) {
        this.groups = groups;
        this.labels = labels;
        this.networkName = networkName;
    }

    public List<String> getGroups() {
        return groups;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public String getNetworkName() {
        return networkName;
    }
}
