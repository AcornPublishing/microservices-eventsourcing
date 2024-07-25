package io.cosmos.eventsourcing.core;

import java.util.Optional;

public interface MessageMapper {
    //
    Optional<Message> map(Event event);
}
