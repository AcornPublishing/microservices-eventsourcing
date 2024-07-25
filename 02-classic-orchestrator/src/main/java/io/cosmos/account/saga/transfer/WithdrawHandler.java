package io.cosmos.account.saga.transfer;

import io.cosmos.account.command.Withdraw;
import io.cosmos.account.exception.NotEnoughBalanceException;
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
    public void on(Withdraw command) {
        //
        this.accountService.withdraw(command);
    }
}
