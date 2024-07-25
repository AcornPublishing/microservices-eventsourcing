package io.cosmos.account.store;

import io.cosmos.account.aggregate.Account;
import io.cosmos.account.store.jpa.AccountJpo;
import io.cosmos.account.store.jpa.AccountRepository;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class AccountStore {
    //
    private final AccountRepository accountRepository;

    public AccountStore(AccountRepository accountRepository) {
        //
        this.accountRepository = accountRepository;
    }

    public void create(Account account) {
        //
        this.accountRepository.save(new AccountJpo(account));
    }

    public Account retrieve(String no) {
        //
        Optional<AccountJpo> jpo = this.accountRepository.findById(no);
        if (jpo.isEmpty()) {
            throw new NoSuchElementException();
        }
        return jpo.get().toAccount();
    }

    public void update(Account account) {
        //
        this.accountRepository.save(new AccountJpo(account));
    }
}
