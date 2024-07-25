package io.cosmos.transfer.saga.transfer;

import io.cosmos.saga.core.*;
import io.cosmos.transfer.saga.transfer.command.*;
import io.cosmos.transfer.saga.transfer.event.*;
import io.cosmos.transfer.saga.transfer.event.SagaBegan;
import io.cosmos.transfer.saga.transfer.event.SagaCanceled;
import io.cosmos.transfer.saga.transfer.event.SagaCompleted;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferSaga extends EventSourcedSaga {
    //
    private static final Logger logger = LoggerFactory.getLogger(TransferSaga.class);
    //
    private String transferId;
    private String fromAccountNo;
    private String toAccountNo;
    private int amount;
    private boolean deposited;
    private boolean withdrawed;

    @Override
    public String identifier() {
        //
        return this.transferId;
    }

    public TransferSaga(BeginSaga command) {
        //
        this.apply(new SagaBegan(command.getTransferId(),
                command.getFromAccountNo(),
                command.getToAccountNo(),
                command.getAmount()));
    }

    private void on(SagaBegan event) {
        //
        this.transferId = event.getTransferId();
        this.fromAccountNo = event.getFromAccountNo();
        this.toAccountNo = event.getToAccountNo();
        this.amount = event.getAmount();
        this.startSaga();
    }

    public void deposit() {
        //
        this.apply(new SagaDeposited(this.getTransferId()));
    }

    private void on(SagaDeposited event) {
        //
        this.deposited = true;
        if (this.deposited && this.withdrawed) {
            this.endSaga();
        }
    }

    public void withdraw() {
        //
        this.apply(new SagaWithdrawed(this.transferId));
    }

    private void on(SagaWithdrawed event) {
        //
        this.withdrawed = true;
        if (this.deposited && this.withdrawed) {
            this.endSaga();
        }
    }

    public void complete() {
        //
        try {
            logger.info("TransferSaga.complete() - sleep ...");

            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        this.apply(new SagaCompleted(this.transferId, this.fromAccountNo, this.getToAccountNo(), this.amount));
    }

    private void on(SagaCompleted event) {
        //
        this.endSaga();
    }

    public void cancel(CancelSaga command) {
        //
        this.apply(new SagaCanceled(this.transferId, this.fromAccountNo));
    }

    private void on(SagaCanceled event) {
        //
        this.endSaga();
    }

    @Override
    public boolean completed() {
        //
        return deposited && withdrawed;
    }
}
