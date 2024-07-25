package io.cosmos.saga.store;

import io.cosmos.eventsourcing.core.Event;
import io.cosmos.saga.store.jpa.SagaEventJpo;
import io.cosmos.saga.store.jpa.SagaEventRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class SagaEventStore {
    //
    private final SagaEventRepository sagaEventRepository;

    public SagaEventStore(SagaEventRepository sagaEventRepository) {
        //
        this.sagaEventRepository = sagaEventRepository;
    }

    public List<Event> retrieveUnrelayedEvents() {
        //
        List<SagaEventJpo> eventJpos = this.sagaEventRepository.findAllByRelayed(false);
        return eventJpos.stream().map(SagaEventJpo::toEvent).collect(Collectors.toList());
    }

    public void update(Event event) {
        //
        Optional<SagaEventJpo> eventJpo = this.sagaEventRepository.findById(event.eventId());
        if (eventJpo.isPresent()) {
            eventJpo.get().setRelayed(true);
            this.sagaEventRepository.save(eventJpo.get());
        }
    }
}
