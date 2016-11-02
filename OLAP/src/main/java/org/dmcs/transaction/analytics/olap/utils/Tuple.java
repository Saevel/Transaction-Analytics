package org.dmcs.transaction.analytics.olap.utils;

/**
 * Created by Zielony on 2016-10-13.
 */
public class Tuple<FirstType, SecondType> {

    private FirstType first;

    private SecondType second;

    public Tuple(FirstType first, SecondType second){
        this.first = first;
        this.second = second;
    }

    public FirstType getFirst() {
        return first;
    }

    public SecondType getSecond() {
        return second;
    }
}
