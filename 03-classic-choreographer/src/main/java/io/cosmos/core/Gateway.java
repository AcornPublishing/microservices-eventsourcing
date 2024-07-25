package io.cosmos.core;

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
        System.out.println("Command: " + command);
        this.eventPublisher.publishEvent(command);
    }

    public void publish(Event event) {
        //
        System.out.println("Event: " + event);
        this.eventPublisher.publishEvent(event);
    }
}
