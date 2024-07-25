package io.cosmos.eventsourcing.store;

import io.cosmos.eventsourcing.core.Event;
import io.cosmos.eventsourcing.core.EventSourcedAggregate;
import io.cosmos.eventsourcing.store.jpa.AggregateEventJpo;
import io.cosmos.eventsourcing.store.jpa.AggregateEventRepository;
import io.cosmos.eventsourcing.store.jpa.AggregateJpo;
import io.cosmos.eventsourcing.store.jpa.AggregateRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class AggregateStore<T extends EventSourcedAggregate> {
    //
    private final AggregateRepository aggregateRepository;
    private final AggregateEventRepository aggregateEventRepository;

    public AggregateStore(AggregateRepository aggregateRepository,
                          AggregateEventRepository sagaEventRepository) {
        //
        this.aggregateRepository = aggregateRepository;
        this.aggregateEventRepository = sagaEventRepository;
    }

    public void save(EventSourcedAggregate aggregate) {
        //
        this.aggregateRepository.save(new AggregateJpo(aggregate));

        List<AggregateEventJpo> eventJpos = aggregate.events().stream().map(event -> {
            return new AggregateEventJpo(aggregate.identifier(), event);
        }).collect(Collectors.toList());
        this.aggregateEventRepository.saveAll(eventJpos);
    }

    public T load(String aggregateId) {
        //
        Optional<AggregateJpo> aggregateJpo = this.aggregateRepository.findById(aggregateId);
        if (aggregateJpo.isEmpty() || aggregateJpo.get().isDeleted()) {
            throw new NoSuchElementException(String.format("Aggregate(%s) is not found.", aggregateId));
        }

        List<AggregateEventJpo> eventJpos = this.aggregateEventRepository.findAllByAggregateIdAndDeletedOrderBySequenceAsc(aggregateId, false);
        List<Event> events = eventJpos.stream().map(AggregateEventJpo::toEvent).collect(Collectors.toList());

        EventSourcedAggregate aggregate = aggregateJpo.get().toAggregate();
        for (Event event: events) {
            aggregate.apply(event, false);
        }
        aggregate.sequence(aggregateJpo.get().getSequence());
        aggregate.setVersion(aggregateJpo.get().getVersion());
        return (T) aggregate;
    }
}
