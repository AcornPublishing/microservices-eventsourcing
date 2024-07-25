package io.cosmos.transfer.store.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<TransferJpo, String> {
}
