package io.cosmos.eventsourcing.store.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AggregateRepository extends JpaRepository<AggregateJpo, String> {
    //
}
