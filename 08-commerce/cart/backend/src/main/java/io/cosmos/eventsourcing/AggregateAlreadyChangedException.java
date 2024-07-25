package io.cosmos.eventsourcing;

public class AggregateAlreadyChangedException extends RuntimeException {
    //
    public AggregateAlreadyChangedException() {
        //
        super("Aggregate is already changed by others.");
    }
}
