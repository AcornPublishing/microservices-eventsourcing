package io.cosmos.eventsourcing;

public class EventHandlerNotFoundException extends RuntimeException {
    //
    public EventHandlerNotFoundException(Class<?> aggregate, Class<?> event) {
        //
        super(String.format("Could not find %s's handler in %s.", event.getSimpleName(), aggregate.getSimpleName()));
    }
}
