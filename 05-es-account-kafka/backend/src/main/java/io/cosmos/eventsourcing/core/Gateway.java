package io.cosmos.eventsourcing.core;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class Gateway {
    //
    private final ApplicationEventPublisher eventPublisher;

    public Gateway(ApplicationEventPublisher eventPublisher) {
        //
        this.eventPublisher = eventPublisher;
    }

    public void send(Command command) {
        //
        this.eventPublisher.publishEvent(command);
    }
}
