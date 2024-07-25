package io.cosmos.account.aggregate;

import io.cosmos.account.command.*;
import io.cosmos.account.event.*;
import io.cosmos.account.exception.BalanceExsitsException;
import io.cosmos.account.exception.NotEnoughBalanceException;
import io.cosmos.eventsourcing.core.EventSourcedAggregate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account extends EventSourcedAggregate {
    //
    private String accountNo;
    private int balance;

    @Override
    public String identifier() {
        //
        return this.accountNo;
    }

    public Account(OpenAccount command) {
        //
        super();

        this.apply(new AccountOpened(command.getAccountNo(), 0));
    }

    private void on(AccountOpened event) {
        //
        this.accountNo = event.getAccountNo();
        this.balance = event.getBalance();
    }

    public void deposit(Deposit command) {
        //
        Deposited event = new Deposited(command.getAccountNo(), command.getAmount(), command.getTransferId());
        event.correlationId(command.getTransferId());
        this.apply(event);
    }

    private void on(Deposited event) {
        //
        this.balance += event.getAmount();
    }

    public void withdraw(Withdraw command) {
        //
        if (this.balance < command.getAmount()) {
            WithdrawFailed event = new WithdrawFailed(this.balance, command.getAmount(), command.getTransferId());
            event.correlationId(command.getTransferId());
            this.apply(event);
            throw new NotEnoughBalanceException();
        }

        Withdrawed event = new Withdrawed(command.getAccountNo(), command.getAmount(), command.getTransferId());
        event.correlationId(command.getTransferId());
        this.apply(event);
    }

    private void on(WithdrawFailed event) {
        //
    }

    private void on(Withdrawed event) {
        //
        this.balance -= event.getAmount();
    }

    public void cancelDeposit(CancelDeposit command) {
        //
        DepositCanceled event = new DepositCanceled();
        event.correlationId(command.getTransferId());

        this.apply(event);
    }

    private void on(DepositCanceled event) {
        //
    }

    public void close(CloseAccount command) {
        //
        if (this.balance > 0) {
            throw new BalanceExsitsException();
        }

        this.apply(new AccountClosed());
    }

    private void on(AccountClosed event) {
        //
        this.markDelete();
    }
}
