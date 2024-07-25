package io.cosmos.transfer.saga.transfer;

import io.cosmos.account.event.Deposited;
import io.cosmos.account.event.WithdrawFailed;
import io.cosmos.account.event.Withdrawed;
import io.cosmos.saga.store.SagaStore;
import io.cosmos.saga.store.SagaTimeoutStore;
import io.cosmos.saga.time.SagaTimeExpired;
import io.cosmos.saga.time.SagaTimeout;
import io.cosmos.transfer.command.CancelTransfer;
import io.cosmos.transfer.command.CompleteTransfer;
import io.cosmos.transfer.event.TransferCreated;
import io.cosmos.transfer.saga.transfer.command.*;
import io.cosmos.transfer.saga.transfer.event.SagaCanceled;
import io.cosmos.transfer.saga.transfer.event.SagaCompleted;
import io.cosmos.transfer.saga.transfer.event.SagaDeposited;
import io.cosmos.transfer.saga.transfer.event.SagaWithdrawed;
import io.cosmos.transfer.service.TransferService;
import io.cosmos.transfer.websocket.TransferWebSockets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Component
public class TransferSagaCoordinator {
    //
    private static final Logger logger = LoggerFactory.getLogger(TransferSagaCoordinator.class);
    //
    private final String SAGA_NAME = "Transfer";
    //
    private final ApplicationEventPublisher eventPublisher;
    private final TaskScheduler taskScheduler;
    //
    private final TransferService transferService;
    private final SagaStore<TransferSaga> sagaStore;
    private final SagaTimeoutStore sagaTimeoutStore;
    //
    private final TransferWebSockets transferWebSockets;

    public TransferSagaCoordinator(ApplicationEventPublisher eventPublisher,
                                   TaskScheduler taskScheduler,
                                   TransferService transferService,
                                   SagaStore<TransferSaga> sagaStore,
                                   SagaTimeoutStore sagaTimeoutStore,
                                   TransferWebSockets transferWebSockets) {
        //
        this.eventPublisher = eventPublisher;
        this.taskScheduler = taskScheduler;
        //
        this.transferService = transferService;
        this.sagaStore = sagaStore;
        this.sagaTimeoutStore = sagaTimeoutStore;
        this.transferWebSockets = transferWebSockets;
    }

    @EventListener
    public void on(TransferCreated event) {
        //
        BeginSaga command = new BeginSaga(event.getTransferId(),
                event.getFromAccountNo(),
                event.getToAccountNo(),
                event.getAmount());
        TransferSaga saga = new TransferSaga(command);
        this.sagaStore.save(saga);

        Date expireTime = SagaTimeout.expireTime(10);
        SagaTimeout sagaTimeout = new SagaTimeout(event.getTransferId(), SAGA_NAME,
                expireTime, this.eventPublisher);
        this.taskScheduler.schedule(sagaTimeout, expireTime);

        this.sagaTimeoutStore.create(sagaTimeout);
    }

    @Retryable
    @EventListener
    public void on(Withdrawed event) {
        //
        logger.info("TransferSagaCoordinator.on(Withdrawed);");

        if (event.getTransferId() == null) {
            return;
        }

        TransferSaga saga = this.sagaStore.load(event.getTransferId());

        if (saga.inSaga()) {
            saga.withdraw();
            this.sagaStore.save(saga);
        }
    }

    @Retryable
    @EventListener
    public void on(Deposited event) {
        //
        logger.info("TransferSagaCoordinator.on(Deposited);");

        if (event.getTransferId() == null) {
            return;
        }

        TransferSaga saga = this.sagaStore.load(event.getTransferId());
        if (saga.inSaga()) {
            saga.deposit();
            this.sagaStore.save(saga);
        }
    }

    @EventListener
    public void on(SagaWithdrawed event) {
        //
        logger.info("TransferSagaCoordinator.on(SagaWithdrawed);");

        TransferSaga saga = this.sagaStore.load(event.getTransferId());
        logger.info(String.format("inSaga: %s, completed: %s", saga.inSaga(), saga.completed()));
        if (saga.inSaga()) {
            saga.complete();
            this.sagaStore.save(saga);
        }
    }

    @EventListener
    public void on(SagaDeposited event) {
        //
        logger.info("TransferSagaCoordinator.on(SagaDeposited);");

        TransferSaga saga = this.sagaStore.load(event.getTransferId());
        logger.info(String.format("inSaga: %s, completed: %s", saga.inSaga(), saga.completed()));
        if (saga.completed()) {
            saga.complete();
            this.sagaStore.save(saga);
        }
    }

    @EventListener
    public void on(SagaCompleted event) throws IOException {
        //
        logger.info("TransferSagaCoordinator.on(SagaCompleted);");

        CompleteTransfer command = new CompleteTransfer(event.getTransferId());
        this.transferService.complete(command);

        SagaTimeout sagaTimeout = this.sagaTimeoutStore.retrieve(event.getTransferId());
        sagaTimeout.complete();
        this.sagaTimeoutStore.update(sagaTimeout);

        Optional<WebSocketSession> session = this.transferWebSockets.find(event.getTransferId());
        if (session.isPresent()) {
            session.get().sendMessage(new TextMessage(event.getTransferId()));
        }
    }

    @EventListener
    public void on(SagaTimeExpired event) {
        //
        logger.info("TransferSagaCoordinator.on(SagaTimeExpired);");

        if (!SAGA_NAME.equals(event.getSagaType())) {
            return;
        }

        TransferSaga saga = this.sagaStore.load(event.getCorrelationId());

        if (saga.inSaga()) {
            saga.cancel(new CancelSaga(event.getCorrelationId()));
            this.sagaStore.save(saga);
        }
    }

    @Retryable
    @EventListener
    public void on(WithdrawFailed event) {
        //
        logger.info("TransferSagaCoordinator.on(WithdrawFailed);");

        if (event.getTransferId() == null) {
            return;
        }

        TransferSaga saga = this.sagaStore.load(event.getTransferId());

        if (saga.inSaga()) {
            saga.cancel(new CancelSaga(event.getTransferId()));
            this.sagaStore.save(saga);
        }
    }

    @EventListener
    public void on(SagaCanceled event) {
        //
        logger.info("TransferSagaCoordinator.on(SagaCanceled);");

        CancelTransfer command = new CancelTransfer(event.getTransferId());
        this.transferService.cancel(command);

        SagaTimeout sagaTimeout = this.sagaTimeoutStore.retrieve(event.getTransferId());
        sagaTimeout.complete();
        this.sagaTimeoutStore.update(sagaTimeout);
    }

}
