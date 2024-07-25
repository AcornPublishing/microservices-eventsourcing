package io.cosmos.eventsourcing.core;

public abstract class Command implements Message {
    //
    private long version;

    public String topic() {
        //
        return "";
    }
}
