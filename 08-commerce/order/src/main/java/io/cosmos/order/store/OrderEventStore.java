package io.cosmos.order.store;

import io.cosmos.eventsourcing.Event;
import io.cosmos.order.store.jpa.OrderEventJpo;
import io.cosmos.order.store.jpa.OrderEventRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class OrderEventStore {
    //
    private final OrderEventRepository orderEventRepository;

    public OrderEventStore(OrderEventRepository orderEventRepository) {
        //
        this.orderEventRepository = orderEventRepository;
    }

    public List<Event> retrieveUnrelayedEvents() {
        //
        List<OrderEventJpo> eventJpos = this.orderEventRepository.findAllByRelayed(false);
        return eventJpos.stream().map(OrderEventJpo::toEvent).collect(Collectors.toList());
    }

    public void update(Event event) {
        //
        Optional<OrderEventJpo> eventJpo = this.orderEventRepository.findById(event.eventId());
        if (eventJpo.isPresent()) {
            eventJpo.get().setRelayed(true);
            this.orderEventRepository.save(eventJpo.get());
        }
    }
}
