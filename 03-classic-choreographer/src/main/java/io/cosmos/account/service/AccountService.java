package io.cosmos.account.service;

import io.cosmos.account.aggregate.Account;
import io.cosmos.account.command.CancelDeposit;
import io.cosmos.account.command.CreateAccount;
import io.cosmos.account.command.Deposit;
import io.cosmos.account.command.Withdraw;
import io.cosmos.account.event.Deposited;
import io.cosmos.account.event.WithdrawFailed;
import io.cosmos.account.event.Withdrawed;
import io.cosmos.account.exception.NotEnoughBalanceException;
import io.cosmos.account.query.QueryAccount;
import io.cosmos.account.store.AccountStore;
import io.cosmos.core.Gateway;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService {
    //
    private final AccountStore accountStore;
    private final Gateway gateway;

    public AccountService(AccountStore accountStore,
                          Gateway gateway) {
        //
        this.accountStore = accountStore;
        this.gateway = gateway;
    }

    public String createAccount(CreateAccount command) {
        //
        String newNo = UUID.randomUUID().toString().split("-")[0];
        command.setNo(newNo);
        Account account = new Account(command);
        this.accountStore.create(account);
        return newNo;
    }

    public Account queryAccount(QueryAccount query) {
        //
        return this.accountStore.retrieve(query.getNo());
    }

    public void deposit(Deposit command) {
        //
        Account account = this.accountStore.retrieve(command.getNo());
        account.deposit(command);
        this.accountStore.update(account);

        if (command.getTransferId().isPresent()) {
            this.gateway.publish(new Deposited(command.getNo(),
                    command.getFromAccountNo(),
                    command.getAmount(),
                    command.getTransferId()));
        }
    }

    public void cancelDeposit(CancelDeposit command) {
        //
        Account account = this.accountStore.retrieve(command.getNo());
        account.cancelDeposit(command);
        this.accountStore.update(account);
    }

    public void withdraw(Withdraw command) {
        //
        Account account = this.accountStore.retrieve(command.getNo());

        try {
            account.withdraw(command);
            this.accountStore.update(account);

            if (command.getTransferId().isPresent()) {
                this.gateway.publish(new Withdrawed(command.getNo(),
                        command.getAmount(),
                        command.getTransferId()));
            }
        } catch (NotEnoughBalanceException e) {
            this.gateway.publish(new WithdrawFailed(command.getTransferId()));
        }
    }
}
