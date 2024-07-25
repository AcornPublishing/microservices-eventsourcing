package io.cosmos.eventsourcing;

public class EventHandlerInvokeException extends RuntimeException {
    //
    public EventHandlerInvokeException(Class<?> aggregate, Class<?> event) {
        //
        super(String.format("Could not invoke %s.on(%s).", aggregate.getSimpleName(), event.getSimpleName()));
    }
}
