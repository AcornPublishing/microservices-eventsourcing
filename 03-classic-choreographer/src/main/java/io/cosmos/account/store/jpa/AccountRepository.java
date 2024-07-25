package io.cosmos.account.store.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountJpo, String> {
    //
}
