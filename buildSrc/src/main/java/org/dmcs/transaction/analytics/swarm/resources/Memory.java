package org.dmcs.transaction.analytics.swarm.resources;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = MemorySerializer.class)
@JsonDeserialize(using = MemoryDeserializer.class)
public class Memory {

    private int value;

    private MemoryUnit unit;

    public Memory(int value, MemoryUnit unit){
        this.value = value;
        this.unit = unit;
    }

    public int getValue() {
        return value;
    }

    public MemoryUnit getUnit() {
        return unit;
    }
}