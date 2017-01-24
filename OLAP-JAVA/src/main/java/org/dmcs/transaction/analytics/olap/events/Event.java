package org.dmcs.transaction.analytics.olap.events;

import java.sql.Timestamp;

/**
 * Created by Zielony on 2016-10-16.
 */
public abstract class Event {

    private Timestamp timestamp;

    public Event(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
