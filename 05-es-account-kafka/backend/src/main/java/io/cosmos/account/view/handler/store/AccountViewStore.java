package io.cosmos.account.view.handler.store;

import io.cosmos.account.view.AccountView;
import io.cosmos.account.view.handler.store.jpa.AccountViewJpo;
import io.cosmos.account.view.handler.store.jpa.AccountViewRepository;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class AccountViewStore {
    //
    private final AccountViewRepository accountViewRepository;

    public AccountViewStore(AccountViewRepository accountViewRepository) {
        //
        this.accountViewRepository = accountViewRepository;
    }

    public void create(AccountView view) {
        //
        this.accountViewRepository.save(new AccountViewJpo(view));
    }

    public AccountView retrieve(String accountNo) {
        //
        Optional<AccountViewJpo> jpo = this.accountViewRepository.findById(accountNo);
        if (jpo.isEmpty()) {
            throw new NoSuchElementException(String.format("AccountView(%s) is not found.", accountNo));
        }
        return jpo.get().toView();
    }

    public void update(AccountView view) {
        //
        this.accountViewRepository.save(new AccountViewJpo(view));
    }

    public boolean exists(String accountNo) {
        //
        return this.accountViewRepository.existsById(accountNo);
    }
}