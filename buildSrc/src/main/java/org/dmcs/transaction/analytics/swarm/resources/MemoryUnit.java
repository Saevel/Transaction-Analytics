package org.dmcs.transaction.analytics.swarm.resources;

public enum MemoryUnit {

    Megabytes('M');

    private char value;

    MemoryUnit(char value){
        this.value = value;
    }

    public char getValue() {
        return value;
    }
}
