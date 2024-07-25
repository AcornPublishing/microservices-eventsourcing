package io.cosmos.eventsourcing.store;

import io.cosmos.eventsourcing.core.Event;
import io.cosmos.eventsourcing.store.jpa.AggregateEventJpo;
import io.cosmos.eventsourcing.store.jpa.AggregateEventRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class AggregateEventStore {
    //
    private final AggregateEventRepository aggregateEventRepository;

    public AggregateEventStore(AggregateEventRepository sagaEventRepository) {
        //
        this.aggregateEventRepository = sagaEventRepository;
    }

    public List<Event> retrieveUnrelayedEvents() {
        //
        List<AggregateEventJpo> eventJpos = this.aggregateEventRepository.findAllByRelayed(false);
        return eventJpos.stream().map(AggregateEventJpo::toEvent).collect(Collectors.toList());
    }

    public void update(Event event) {
        //
        Optional<AggregateEventJpo> eventJpo = this.aggregateEventRepository.findById(event.eventId());
        if (eventJpo.isPresent()) {
            eventJpo.get().setRelayed(true);
            this.aggregateEventRepository.save(eventJpo.get());
        }
    }

    public void markDelete(String transferId) {
        //
        List<AggregateEventJpo> eventJpos = this.aggregateEventRepository.findByCorrelationId(transferId);
        eventJpos.stream().forEach(jpo -> {jpo.setDeleted(true);});
        this.aggregateEventRepository.saveAll(eventJpos);
    }

    public List<Event> retrieveEvents(String accountNo) {
        //
        List<AggregateEventJpo> eventJpos = this.aggregateEventRepository.findByAggregateIdOrderByTimeDesc(accountNo);
        return eventJpos.stream().map(AggregateEventJpo::toEvent).collect(Collectors.toList());
    }
}
