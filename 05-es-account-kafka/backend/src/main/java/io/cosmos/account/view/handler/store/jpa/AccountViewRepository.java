package io.cosmos.account.view.handler.store.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountViewRepository extends JpaRepository<AccountViewJpo, String> {
}
