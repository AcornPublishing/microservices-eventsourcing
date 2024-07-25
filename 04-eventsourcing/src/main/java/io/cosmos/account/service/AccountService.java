package io.cosmos.account.service;

import io.cosmos.account.aggregate.Account;
import io.cosmos.account.command.*;
import io.cosmos.account.exception.NotEnoughBalanceException;
import io.cosmos.account.query.QueryAccount;
import io.cosmos.eventsourcing.store.AggregateEventStore;
import io.cosmos.eventsourcing.store.AggregateStore;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class AccountService {
    //
    private final AggregateStore<Account> aggregateStore;
    private final AggregateEventStore aggregateEventStore;

    public AccountService(AggregateStore aggregateStore,
                          AggregateEventStore aggregateEventStore) {
        //
        this.aggregateStore = aggregateStore;
        this.aggregateEventStore = aggregateEventStore;
    }

    public String openAccount(OpenAccount command) {
        //
        String newAccountNo = UUID.randomUUID().toString().split("-")[0];
        command.setAccountNo(newAccountNo);

        Account account = new Account(command);
        this.aggregateStore.save(account);

        return newAccountNo;
    }

    public Account queryAccount(QueryAccount query) {
        //
        return this.aggregateStore.load(query.getAccountNo());
    }

    public void deposit(Deposit command) {
        //
        Account account = this.aggregateStore.load(command.getAccountNo());
        account.deposit(command);
        this.aggregateStore.save(account);
    }

    public void cancelDeposit(CancelDeposit command) {
        //
        Account account = this.aggregateStore.load(command.getAccountNo());
        account.cancelDeposit(command);
        this.aggregateStore.save(account);

        this.aggregateEventStore.markDelete(command.getTransferId());
    }

    //@Transactional(dontRollbackOn = { NotEnoughBalanceException.class })
    public void withdraw(Withdraw command) {
        //
        Account account = this.aggregateStore.load(command.getAccountNo());
        try {
            account.withdraw(command);
            this.aggregateStore.save(account);
        } catch (NotEnoughBalanceException e) {
            this.aggregateStore.save(account);
            throw e;
        }
    }

    public void close(CloseAccount command) {
        //
        Account account = this.aggregateStore.load(command.getAccountNo());
        account.close(command);
        this.aggregateStore.save(account);
    }
}
