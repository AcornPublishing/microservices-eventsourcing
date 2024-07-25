package io.cosmos.account.saga.transfer;

import io.cosmos.account.command.CancelDeposit;
import io.cosmos.account.command.Deposit;
import io.cosmos.account.command.Withdraw;
import io.cosmos.account.service.AccountService;
import io.cosmos.transfer.saga.transfer.event.TransferSagaBegan;
import io.cosmos.transfer.saga.transfer.event.TransferSagaCanceled;
import org.springframework.context.event.EventListener;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
public class TransferAccountSagaCoordinator {
    //
    private final AccountService accountService;

    public TransferAccountSagaCoordinator(AccountService accountService) {
        //
        this.accountService = accountService;
    }

    @Retryable(exclude = { ObjectOptimisticLockingFailureException.class })
    @EventListener
    public void onDeposit(TransferSagaBegan event) {
        //
        /*
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        */

        Deposit command = new Deposit(event.getToAccountNo(), event.getAmount(), event.getTransferId());
        this.accountService.deposit(command);
    }

    @EventListener
    public void onWithdraw(TransferSagaBegan event) {
        //
        Withdraw command = new Withdraw(event.getFromAccountNo(), event.getAmount(), event.getTransferId());
        try {
            this.accountService.withdraw(command);
        } catch (Exception e) {

        }
    }

    @EventListener
    public void on(TransferSagaCanceled event) {
        //
        CancelDeposit command = new CancelDeposit(event.getAccountNo(), event.getTransferId());
        this.accountService.cancelDeposit(command);
    }
}
