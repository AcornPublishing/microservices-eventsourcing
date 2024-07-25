package io.cosmos.saga.store;

import io.cosmos.saga.store.jpa.SagaTimeoutJpo;
import io.cosmos.saga.store.jpa.SagaTimeoutRepository;
import io.cosmos.saga.time.SagaTimeout;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SagaTimeoutStore {
    //
    private final SagaTimeoutRepository sagaTimeoutRepository;
    private final ApplicationEventPublisher eventPublisher;

    public SagaTimeoutStore(SagaTimeoutRepository sagaTimeoutRepository,
                            ApplicationEventPublisher eventPublisher) {
        //
        this.sagaTimeoutRepository = sagaTimeoutRepository;
        this.eventPublisher = eventPublisher;
    }

    public void create(SagaTimeout sagaTimeout) {
        //
        this.sagaTimeoutRepository.save(new SagaTimeoutJpo(sagaTimeout));
    }

    public List<SagaTimeout> retrievePendingTimeout() {
        //
        return this.sagaTimeoutRepository.findAllByComplete(false).stream().map(jpo -> {
            return new SagaTimeout(jpo.getCorrelationId(), jpo.getSagaType(), jpo.getExpireTime(), eventPublisher);
        }).collect(Collectors.toList());
    }

    public SagaTimeout retrieve(String transferId) {
        //
        return this.sagaTimeoutRepository.findById(transferId).get().toSagaTimeout(eventPublisher);
    }

    public void update(SagaTimeout sagaTimeout) {
        //
        this.sagaTimeoutRepository.save(new SagaTimeoutJpo(sagaTimeout));
    }
}
