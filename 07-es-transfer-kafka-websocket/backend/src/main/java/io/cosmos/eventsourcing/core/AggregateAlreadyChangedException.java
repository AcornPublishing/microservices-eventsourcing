package io.cosmos.eventsourcing.core;

public class AggregateAlreadyChangedException extends RuntimeException {
    //
    public AggregateAlreadyChangedException() {
        //
        super("Aggregate is already changed by others.");
    }
}
