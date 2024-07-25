package io.cosmos.account.aggregate;

import io.cosmos.account.command.CancelDeposit;
import io.cosmos.account.command.CreateAccount;
import io.cosmos.account.command.Deposit;
import io.cosmos.account.command.Withdraw;
import io.cosmos.account.exception.NotEnoughBalanceException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class Account {
    //
    @Getter
    @Setter
    private String no;
    @Getter
    @Setter
    private int balance;

    private long version;

    public Account(CreateAccount command) {
        //
        this.no = command.getNo();
        this.balance = 0;
    }

    public void deposit(Deposit command) {
        //
        this.balance += command.getAmount();
    }

    public void cancelDeposit(CancelDeposit command) {
        //
        this.balance -= command.getAmount();
    }

    public void withdraw(Withdraw command) {
        //
        if (this.balance < command.getAmount()) {
            throw new NotEnoughBalanceException();
        }

        this.balance -= command.getAmount();
    }

    public long version() {
        //
        return this.version;
    }

    public void version(long version) {
        //
        this.version = version;
    }
}
