package io.cosmos.eventsourcing;

public interface Upcaster {
    //
    public Event upcast(Event event);
}
