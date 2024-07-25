package io.cosmos.saga.relay;

import io.cosmos.eventsourcing.core.Event;
import io.cosmos.saga.store.SagaEventStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SagaEventRelay {
    //
    private static final Logger logger = LoggerFactory.getLogger(SagaEventRelay.class);
    //
    private final SagaEventStore sagaEventStore;
    private final ApplicationEventPublisher eventPublisher;

    public SagaEventRelay(SagaEventStore sagaEventStore,
                          ApplicationEventPublisher eventPublisher) {
        //
        this.sagaEventStore = sagaEventStore;
        //
        this.eventPublisher = eventPublisher;
    }

    @Scheduled(fixedDelay = 100)
    public void publish() {
        List<Event> events = this.sagaEventStore.retrieveUnrelayedEvents();
        events.stream().forEach(event -> {
            if (event.propagate()) {
                logger.info("SagaEventRelay.publish(" + event.getClass().getName() + ")");
                eventPublisher.publishEvent(event);
            }
            this.sagaEventStore.update(event);
        });
    }
}
