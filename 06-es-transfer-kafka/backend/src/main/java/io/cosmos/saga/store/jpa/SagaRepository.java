package io.cosmos.saga.store.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SagaRepository extends JpaRepository<SagaJpo, String> {
    //
}
