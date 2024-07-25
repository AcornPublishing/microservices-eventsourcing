package io.cosmos.saga.store;

import io.cosmos.eventsourcing.core.Event;
import io.cosmos.saga.core.EventSourcedSaga;
import io.cosmos.saga.store.jpa.SagaEventJpo;
import io.cosmos.saga.store.jpa.SagaEventRepository;
import io.cosmos.saga.store.jpa.SagaJpo;
import io.cosmos.saga.store.jpa.SagaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class SagaStore<T extends EventSourcedSaga> {
    //
    private final SagaRepository sagaRepository;
    private final SagaEventRepository sagaEventRepository;

    public SagaStore(SagaRepository sagaRepository,
                     SagaEventRepository sagaEventRepository) {
        //
        this.sagaRepository = sagaRepository;
        this.sagaEventRepository = sagaEventRepository;
    }

    public void save(EventSourcedSaga saga) {
        //
        this.sagaRepository.save(new SagaJpo(saga));

        List<SagaEventJpo> eventJpos = saga.events().stream().map(event -> {
            return new SagaEventJpo(saga.identifier(), event);
        }).collect(Collectors.toList());
        this.sagaEventRepository.saveAll(eventJpos);
    }

    public T load(String sagaId) {
        //
        Optional<SagaJpo> sagaJpo = this.sagaRepository.findById(sagaId);
        if (sagaJpo.isEmpty()) {
            throw new NoSuchElementException(String.format("Saga(%s) is not found.", sagaId));
        }

        List<SagaEventJpo> eventJpos = this.sagaEventRepository.findAllBySagaIdOrderBySequenceAsc(sagaId);
        List<Event> events = eventJpos.stream().map(SagaEventJpo::toEvent).collect(Collectors.toList());

        EventSourcedSaga saga = sagaJpo.get().toSaga();
        for (Event event: events) {
            saga.apply(event, false);
        }
        saga.sequence(sagaJpo.get().getSequence());
        saga.setVersion(sagaJpo.get().getVersion());
        return (T) saga;
    }

    public List<String> loadAll() {
        //
        List<SagaJpo> sagaJpos = this.sagaRepository.findAll();
        return sagaJpos.stream().map(SagaJpo::getId).collect(Collectors.toList());
    }
}