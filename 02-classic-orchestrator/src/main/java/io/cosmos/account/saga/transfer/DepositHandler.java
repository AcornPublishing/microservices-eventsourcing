package io.cosmos.account.saga.transfer;

import io.cosmos.account.command.CancelDeposit;
import io.cosmos.account.command.Deposit;
import io.cosmos.account.service.AccountService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DepositHandler {
    //
    private final AccountService accountService;

    public DepositHandler(AccountService accountService) {
        //
        this.accountService = accountService;
    }

    @EventListener
    public void on(Deposit command) {
        //
        this.accountService.deposit(command);
    }

    @EventListener
    public void on(CancelDeposit command) {
        //
        this.accountService.cancelDeposit(command);
    }
}
