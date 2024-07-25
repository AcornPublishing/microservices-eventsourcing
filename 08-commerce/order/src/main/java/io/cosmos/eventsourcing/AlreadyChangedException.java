package io.cosmos.eventsourcing;

public class AlreadyChangedException extends RuntimeException {
    //
    public AlreadyChangedException() {
        //
        super("Aggregate is already changed by others.");
    }
}
