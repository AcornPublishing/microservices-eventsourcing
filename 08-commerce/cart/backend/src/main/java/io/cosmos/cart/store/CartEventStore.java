package io.cosmos.cart.store;

import io.cosmos.cart.store.jpa.CartEventJpo;
import io.cosmos.cart.store.jpa.CartEventRepository;
import io.cosmos.eventsourcing.Event;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CartEventStore {
    //
    private final CartEventRepository cartEventRepository;

    public CartEventStore(CartEventRepository cartEventRepository) {
        //
        this.cartEventRepository = cartEventRepository;
    }

    public List<Event> retrieveUnrelayedEvents() {
        //
        List<CartEventJpo> eventJpos = this.cartEventRepository.findAllByRelayed(false);
        return eventJpos.stream().map(CartEventJpo::toEvent).collect(Collectors.toList());
    }

    public void update(Event event) {
        //
        Optional<CartEventJpo> eventJpo = this.cartEventRepository.findById(event.eventId());
        if (eventJpo.isPresent()) {
            eventJpo.get().setRelayed(true);
            this.cartEventRepository.save(eventJpo.get());
        }
    }

    public void removeByCorrelationId(String correlationId) {
        //
        List<CartEventJpo> eventJpos = this.cartEventRepository.findAllByCorrelationId(correlationId);
        eventJpos.forEach(eventJpo -> {
            eventJpo.setDeleted(true);
        });
        this.cartEventRepository.saveAll(eventJpos);
    }
}
