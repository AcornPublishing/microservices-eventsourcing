package io.cosmos.order.relay;

import io.cosmos.eventsourcing.Event;
import io.cosmos.eventsourcing.EventMapper;
import io.cosmos.order.store.OrderEventStore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderEventRelay {
    //
    private final ApplicationContext applicationContext;
    //
    private final OrderEventStore orderEventStore;
    //
    private final ApplicationEventPublisher eventPublisher;
    //
    private final List<EventMapper> eventMappers;

    public OrderEventRelay(ApplicationContext applicationContext,
                           OrderEventStore orderEventStore,
                           ApplicationEventPublisher eventPublisher) {
        //
        this.applicationContext = applicationContext;
        //
        this.orderEventStore = orderEventStore;
        //
        this.eventPublisher = eventPublisher;
        this.eventMappers = new ArrayList<>();

        Arrays.stream(applicationContext.getBeanNamesForType(EventMapper.class)).forEach(name -> {
            this.eventMappers.add(applicationContext.getBean(name, EventMapper.class));
        });
    }

    @Scheduled(fixedDelay = 100)
    public void publish() {
        List<Event> events = this.orderEventStore.retrieveUnrelayedEvents();
        events.stream().forEach(event -> {
            if (event.propagate()) {
                eventPublisher.publishEvent(event);
            }

            List<Optional<Event>> mappedEvents = this.mappedEvent(event);
            mappedEvents.forEach(mappedEvent -> {
                if (mappedEvent.isPresent()) {
                    eventPublisher.publishEvent(mappedEvent.get());
                }
            });

            this.orderEventStore.update(event);
        });
    }

    private List<Optional<Event>> mappedEvent(Event event) {
        //
        return this.eventMappers.stream().map(mapper -> {
            return mapper.map(event);
        }).collect(Collectors.toList());
    }
}
