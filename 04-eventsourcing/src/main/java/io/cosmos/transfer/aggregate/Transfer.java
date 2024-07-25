package io.cosmos.transfer.aggregate;

import io.cosmos.eventsourcing.core.EventSourcedAggregate;
import io.cosmos.transfer.command.CancelTransfer;
import io.cosmos.transfer.command.CompleteTransfer;
import io.cosmos.transfer.command.TransferMoney;
import io.cosmos.transfer.event.TransferCanceled;
import io.cosmos.transfer.event.TransferCompleted;
import io.cosmos.transfer.event.TransferCreated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transfer extends EventSourcedAggregate {
    //
    private String transferId;
    private String fromAccountNo;
    private String toAccountNo;
    private int amount;
    private State state;

    @Override
    public String identifier() {
        //
        return this.transferId;
    }

    public Transfer(TransferMoney command) {
        //
        TransferCreated event = new TransferCreated(command.getTransferId(),
                command.getFromAccountNo(),
                command.getToAccountNo(),
                command.getAmount());
        event.correlationId(command.getTransferId());

        this.apply(event);
    }

    private void on(TransferCreated event) {
        //
        this.transferId = event.getTransferId();
        this.fromAccountNo = event.getFromAccountNo();
        this.toAccountNo = event.getToAccountNo();
        this.amount = event.getAmount();

        this.state = State.Unknown;

        this.startSaga();
    }

    public void completeTransfer(CompleteTransfer command) {
        //
        TransferCompleted event = new TransferCompleted(command.getTransferId(),
                this.fromAccountNo, this.toAccountNo, this.getAmount());
        event.correlationId(command.getTransferId());

        this.apply(event);
    }

    private void on(TransferCompleted event) {
        //
        this.state = State.Succeed;
        this.endSaga();
    }

    public void cancelTransfer(CancelTransfer command) {
        //
        this.apply(new TransferCanceled(command.getTransferId()));
    }

    private void on(TransferCanceled event) {
        //
        this.state = State.Fail;
        this.endSaga();
    }

}
