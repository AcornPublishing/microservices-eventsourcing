package io.cosmos.account.view.store.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountViewRepository extends JpaRepository<AccountViewJpo, String> {
    //
}
