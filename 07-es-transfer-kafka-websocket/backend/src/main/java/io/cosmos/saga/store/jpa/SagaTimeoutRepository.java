package io.cosmos.saga.store.jpa;

import org.apache.commons.lang3.function.Failable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SagaTimeoutRepository extends JpaRepository<SagaTimeoutJpo, String> {
    //
    List<SagaTimeoutJpo> findAllByComplete(boolean complete);
}
