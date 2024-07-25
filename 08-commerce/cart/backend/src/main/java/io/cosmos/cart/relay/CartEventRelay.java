package io.cosmos.cart.relay;

import io.cosmos.cart.store.CartEventStore;
import io.cosmos.eventsourcing.Event;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartEventRelay {
    //
    private final CartEventStore cartEventStore;
    //
    private final ApplicationEventPublisher eventPublisher;

    public CartEventRelay(CartEventStore cartEventStore,
                          ApplicationEventPublisher eventPublisher) {
        //
        this.cartEventStore = cartEventStore;
        //
        this.eventPublisher = eventPublisher;
    }

    @Scheduled(fixedDelay = 100)
    public void publish() {
        List<Event> events = this.cartEventStore.retrieveUnrelayedEvents();
        events.stream().forEach(event -> {
            if (event.propagate()) {
                eventPublisher.publishEvent(event);
            }
            this.cartEventStore.update(event);
        });
    }
}
