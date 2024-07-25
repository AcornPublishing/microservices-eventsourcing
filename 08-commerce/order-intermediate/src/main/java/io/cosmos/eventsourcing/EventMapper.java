package io.cosmos.eventsourcing;

import java.util.Optional;

public interface EventMapper {
    //
    public Optional<Event> map(Event event);
}
