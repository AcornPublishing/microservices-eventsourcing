package io.cosmos.eventsourcing.relay;

import io.cosmos.eventsourcing.core.Event;
import io.cosmos.eventsourcing.store.AggregateEventStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventRelay {
    //
    private static final Logger logger = LoggerFactory.getLogger(EventRelay.class);
    //
    private final AggregateEventStore aggregateEventStore;
    private final ApplicationEventPublisher eventPublisher;

    public EventRelay(AggregateEventStore aggregateEventStore,
                      ApplicationEventPublisher eventPublisher) {
        //
        this.aggregateEventStore = aggregateEventStore;
        //
        this.eventPublisher = eventPublisher;
    }

    @Scheduled(fixedDelay = 100)
    public void publish() {
        List<Event> events = this.aggregateEventStore.retrieveUnrelayedEvents();
        events.stream().forEach(event -> {
            if (event.propagate()) {
                logger.info("EventRelay.publish(" + event.getClass().getName() + ")");
                eventPublisher.publishEvent(event);
            }
            this.aggregateEventStore.update(event);
        });
    }
}
