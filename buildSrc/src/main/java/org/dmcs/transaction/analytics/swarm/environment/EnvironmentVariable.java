package org.dmcs.transaction.analytics.swarm.environment;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = EnvironmentSerializer.class)
public interface EnvironmentVariable {

    String getValue();
}
