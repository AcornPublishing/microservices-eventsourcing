package io.cosmos.saga.store.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SagaEventRepository extends JpaRepository<SagaEventJpo, String> {
    //
    List<SagaEventJpo> findAllBySagaIdOrderBySequenceAsc(String sagaId);
    List<SagaEventJpo> findAllByRelayed(boolean relayed);
}
