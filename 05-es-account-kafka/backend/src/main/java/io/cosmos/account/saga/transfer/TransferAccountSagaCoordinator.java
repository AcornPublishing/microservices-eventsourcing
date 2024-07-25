package io.cosmos.account.saga.transfer;

import io.cosmos.account.command.*;
import io.cosmos.account.service.AccountService;
import io.cosmos.eventsourcing.store.AggregateEventStore;
import io.cosmos.transfer.saga.transfer.event.SagaBegan;
import io.cosmos.transfer.saga.transfer.event.SagaCanceled;
import io.cosmos.transfer.saga.transfer.event.SagaCompleted;
import org.springframework.context.event.EventListener;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
public class TransferAccountSagaCoordinator {
    //
    private final AccountService accountService;
    private final AggregateEventStore aggregateEventStore;

    public TransferAccountSagaCoordinator(AccountService accountService,
                                          AggregateEventStore aggregateEventStore) {
        //
        this.accountService = accountService;
        this.aggregateEventStore = aggregateEventStore;
    }

    @Retryable(exclude = { ObjectOptimisticLockingFailureException.class })
    @EventListener
    public void onDeposit(SagaBegan event) {
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
    public void onWithdraw(SagaBegan event) {
        //
        Withdraw command = new Withdraw(event.getFromAccountNo(), event.getAmount(), event.getTransferId());
        try {
            this.accountService.withdraw(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventListener
    public void on(SagaCanceled event) {
        //
        /*
        CancelDeposit command = new CancelDeposit(event.getAccountNo(), event.getTransferId());
        this.accountService.cancelDeposit(command);
        */
        this.aggregateEventStore.markDelete(event.getTransferId());
    }

    @EventListener
    public void on(SagaCompleted event) {
        //
        CompleteDeposit completeDeposit = new CompleteDeposit(event.getToAccountNo(), event.getAmount(), event.getTransferId());
        this.accountService.completeDeposit(completeDeposit);
        CompleteWithdraw completeWithdraw = new CompleteWithdraw(event.getFromAccountNo(), event.getAmount(), event.getTransferId());
        this.accountService.completeWithdraw(completeWithdraw);
    }
}
