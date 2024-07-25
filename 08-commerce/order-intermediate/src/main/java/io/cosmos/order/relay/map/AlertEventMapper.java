package io.cosmos.order.relay.map;

import io.cosmos.eventsourcing.Event;
import io.cosmos.eventsourcing.EventMapper;
import io.cosmos.notification.command.PutAlert;
import io.cosmos.order.event.OrderCompleted;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class AlertEventMapper implements EventMapper {
    //
    public Optional<Event> map(Event event) {
        //
        if (event.getClass().isAssignableFrom(OrderCompleted.class)) {
            return this.map(event.toEvent(OrderCompleted.class));
        }

        return Optional.empty();
    }

    public Optional<Event> map(OrderCompleted event) {
        //
        String id = UUID.randomUUID().toString().split("-")[0];
        return Optional.of(new PutAlert(id, "A new order has been placed.", String.format("/order/%s", event.getNo())));
    }
}

