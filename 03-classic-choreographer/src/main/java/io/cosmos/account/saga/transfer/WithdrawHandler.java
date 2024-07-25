package io.cosmos.account.saga.transfer;

import io.cosmos.account.command.Withdraw;
import io.cosmos.account.event.Deposited;
import io.cosmos.account.service.AccountService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class WithdrawHandler {
    //
    private final AccountService accountService;

    public WithdrawHandler(AccountService accountService) {
        //
        this.accountService = accountService;
    }

    @EventListener
    public void on(Deposited event) {
        //
        Withdraw command = new Withdraw(event.getFromAccountNo(), event.getAmount(), event.getTransferId());
        this.accountService.withdraw(command);
    }
}
