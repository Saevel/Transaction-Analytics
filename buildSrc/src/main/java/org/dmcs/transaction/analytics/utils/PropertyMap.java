package org.dmcs.transaction.analytics.utils;

import org.dmcs.transaction.analytics.swarm.environment.EnvironmentVariable;
import org.dmcs.transaction.analytics.swarm.environment.Variable;

public class PropertyMap {

    public static java.util.Properties from(Variable... variables){
        java.util.Properties props = new java.util.Properties();
        for(int i =0; i < variables.length; i++){
            props.setProperty(variables[i].getName(), variables[i].getValue());
        }
        return props;
    }
}
