package org.dmcs.transaction.analytics.swarm;

import org.dmcs.transaction.analytics.swarm.restart.RestartCondition;

public class RestartPolicy {

    private RestartCondition condition;

    public RestartPolicy(RestartCondition condition){
        this.condition = condition;
    }

    public RestartCondition getCondition(){
        return condition;
    }
}
