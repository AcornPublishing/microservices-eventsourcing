package io.cosmos.transfer.saga.transfer;

import io.cosmos.saga.core.*;
import io.cosmos.transfer.saga.transfer.command.*;
import io.cosmos.transfer.saga.transfer.event.*;
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
    private String toAccountNo;
    private boolean deposited;
    private boolean withdrawed;

    @Override
    public String identifier() {
        //
        return this.transferId;
    }

    public TransferSaga(BeginTransferSaga command) {
        //
        this.apply(new TransferSagaBegan(command.getTransferId(),
                command.getFromAccountNo(),
                command.getToAccountNo(),
                command.getAmount()));
    }

    private void on(TransferSagaBegan event) {
        //
        this.transferId = event.getTransferId();
        this.toAccountNo = event.getToAccountNo();
    }

    public void deposit(DepositTransferSaga command) {
        //
        this.apply(new TransferSagaDeposited());
    }

    private void on(TransferSagaDeposited event) {
        //
        this.deposited = true;
    }

    public void withdraw(WithdrawTransferSaga command) {
        //
        this.apply(new TransferSagaWithdrawed());
    }

    private void on(TransferSagaWithdrawed event) {
        //
        this.withdrawed = true;
    }

    public void complete(CompleteTransferSaga command) {
        //
        this.apply(new TransferSagaCompleted());
    }

    private void on(TransferSagaCompleted event) {
        //
        this.setCompleteSaga(true);
    }

    public void cancel(CancelTransferSaga command) {
        //
        this.apply(new TransferSagaCanceled(this.toAccountNo, this.transferId));
    }

    private void on(TransferSagaCanceled event) {
        //
        this.setCompleteSaga(true);
    }

    @Override
    public boolean completed() {
        //
        return deposited && withdrawed;
    }
}
