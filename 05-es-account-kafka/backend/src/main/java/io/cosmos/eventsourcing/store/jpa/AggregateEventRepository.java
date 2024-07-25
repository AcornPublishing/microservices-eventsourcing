package io.cosmos.eventsourcing.store.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AggregateEventRepository extends JpaRepository<AggregateEventJpo, String> {
    //
    List<AggregateEventJpo> findAllByAggregateIdAndDeletedOrderBySequenceAsc(String aggregateId, boolean deleted);
    List<AggregateEventJpo> findAllByRelayed(boolean relayed);
    List<AggregateEventJpo> findByCorrelationId(String transferId);

    List<AggregateEventJpo> findByAggregateIdOrderByTimeDesc(String aggregateId);
}
