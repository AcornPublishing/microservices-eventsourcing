package io.cosmos.transfer.aggregate;

import io.cosmos.transfer.command.CancelTransfer;
import io.cosmos.transfer.command.CompleteTransfer;
import io.cosmos.transfer.command.TransferMoney;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {
    //
    private String transferId;
    //
    private String fromAccount;
    private  String toAccount;
    private int amount;
    //
    private boolean completed;
    //
    private long version;

    public Transfer(TransferMoney command) {
        //
        this.transferId = command.getTransferId();
        this.fromAccount = command.getFromAccount();
        this.toAccount = command.getToAccount();
        this.amount = command.getAmount();
    }

    public void complete(CompleteTransfer command) {
        //
        this.completed = true;
    }

    public void cancel(CancelTransfer command) {
        //
        this.completed = false;
    }

    public long version() {
        //
        return this.version;
    }

    public void version(long version) {
        //
        this.version = version;
    }
}
