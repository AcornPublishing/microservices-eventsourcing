package io.cosmos.transfer.saga.transfer;

import io.cosmos.account.event.Deposited;
import io.cosmos.account.event.WithdrawFailed;
import io.cosmos.account.event.Withdrawed;
import io.cosmos.saga.store.SagaStore;
import io.cosmos.saga.time.SagaTimeExpired;
import io.cosmos.saga.time.SagaTimeout;
import io.cosmos.transfer.command.CancelTransfer;
import io.cosmos.transfer.command.CompleteTransfer;
import io.cosmos.transfer.event.TransferCanceled;
import io.cosmos.transfer.event.TransferCompleted;
import io.cosmos.transfer.event.TransferCreated;
import io.cosmos.transfer.saga.transfer.command.*;
import io.cosmos.transfer.service.TransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class TransferSagaCoordinator {
    //
    private static final Logger logger = LoggerFactory.getLogger(TransferSagaCoordinator.class);
    //
    private final String SAGA_NAME = "Transfer";
    //
    private final ApplicationEventPublisher applicationEventPublisher;
    private final TaskScheduler taskScheduler;
    //
    private final TransferService transferService;
    private final SagaStore<TransferSaga> sagaStore;

    public TransferSagaCoordinator(ApplicationEventPublisher applicationEventPublisher,
                                   TaskScheduler taskScheduler,
                                   TransferService transferService,
                                   SagaStore<TransferSaga> sagaStore) {
        //
        this.applicationEventPublisher = applicationEventPublisher;
        this.taskScheduler = taskScheduler;
        //
        this.transferService = transferService;
        this.sagaStore = sagaStore;
    }

    @EventListener
    public void on(TransferCreated event) {
        //
        BeginTransferSaga command = new BeginTransferSaga(event.getTransferId(),
                event.getFromAccountNo(),
                event.getToAccountNo(),
                event.getAmount());
        TransferSaga saga = new TransferSaga(command);
        this.sagaStore.save(saga);

        SagaTimeout sagaTimeout = new SagaTimeout(event.getTransferId(), SAGA_NAME, this.applicationEventPublisher);
        this.taskScheduler.schedule(sagaTimeout, SagaTimeout.expireTime(5));
    }

    @EventListener
    public void on(SagaTimeExpired event) {
        //
        logger.info("TransferChoreographer.on(SagaTimeExpired);");

        if (!SAGA_NAME.equals(event.getSagaType())) {
            return;
        }

        TransferSaga saga = this.sagaStore.load(event.getCorrelationId());

        if (!saga.isCompleteSaga()) {
            saga.cancel(new CancelTransferSaga(event.getCorrelationId()));
            this.sagaStore.save(saga);

            if (saga.completed()) {
                CancelTransfer command = new CancelTransfer(event.getCorrelationId());
                this.transferService.cancel(command);
            }
        }
    }

    @Retryable
    @EventListener
    public void on(WithdrawFailed event) {
        //
        if (event.getTransferId() == null) {
            return;
        }

        TransferSaga saga = this.sagaStore.load(event.getTransferId());

        if (!saga.isCompleteSaga()) {
            saga.cancel(new CancelTransferSaga(event.getTransferId()));
            this.sagaStore.save(saga);

            if (saga.completed()) {
                CancelTransfer command = new CancelTransfer(event.getTransferId());
                this.transferService.cancel(command);
            }
        }
    }

    @Retryable
    @EventListener
    public void on(Withdrawed event) {
        //
        if (event.getTransferId() == null) {
            return;
        }

        TransferSaga saga = this.sagaStore.load(event.getTransferId());

        if (!saga.isCompleteSaga()) {
            saga.withdraw(new WithdrawTransferSaga(event.getTransferId()));
            this.sagaStore.save(saga);

            if (saga.completed()) {
                CompleteTransfer command = new CompleteTransfer(event.getTransferId());
                this.transferService.complete(command);
            }
        }
    }

    @Retryable
    @EventListener
    public void on(Deposited event) {
        //
        if (event.getTransferId() == null) {
            return;
        }

        TransferSaga saga = this.sagaStore.load(event.getTransferId());

        if (!saga.isCompleteSaga()) {
            saga.deposit(new DepositTransferSaga(event.getTransferId()));
            this.sagaStore.save(saga);

            if (saga.completed()){
                CompleteTransfer command = new CompleteTransfer(event.getTransferId());
                this.transferService.complete(command);
            }
        }
    }

    @EventListener
    public void on(TransferCompleted event) {
        //
        TransferSaga saga = this.sagaStore.load(event.getTransferId());
        saga.complete(new CompleteTransferSaga(event.getTransferId()));
        this.sagaStore.save(saga);
    }

    @EventListener
    public void on(TransferCanceled event) {
        //
        TransferSaga saga = this.sagaStore.load(event.getTransferId());
        saga.cancel(new CancelTransferSaga(event.getTransferId()));
        this.sagaStore.save(saga);
    }
}
