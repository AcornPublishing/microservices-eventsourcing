package io.cosmos.account.saga.transfer;

import io.cosmos.account.command.CancelDeposit;
import io.cosmos.account.command.Deposit;
import io.cosmos.account.service.AccountService;
import io.cosmos.transfer.event.TransferCreated;
import io.cosmos.transfer.event.TransferCanceled;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DepositHandler {
    //
    private final AccountService accountService;

    public DepositHandler(AccountService accountService) {
        //
        this.accountService = accountService;
    }

    @EventListener
    public void on(TransferCreated event) {
        //
        Deposit command = new Deposit(event.getToAccount(), event.getFromAccount(),
                event.getAmount(), Optional.of(event.getTransferId()));
        this.accountService.deposit(command);
    }

    @EventListener
    public void on(TransferCanceled event) {
        //
        CancelDeposit command = new CancelDeposit(event.getToAccount(),
                event.getAmount(), Optional.of(event.getTransferId()));
        this.accountService.cancelDeposit(command);
    }
}
