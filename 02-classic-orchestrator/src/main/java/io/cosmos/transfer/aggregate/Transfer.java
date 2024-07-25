package io.cosmos.transfer.aggregate;

import io.cosmos.transfer.command.CancelTransfer;
import io.cosmos.transfer.command.CompleteDeposit;
import io.cosmos.transfer.command.CompleteWithdraw;
import io.cosmos.transfer.command.TransferMoney;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class Transfer {
    //
    @Getter
    @Setter
    private String transferId;
    //
    @Getter
    @Setter
    private String fromAccount;
    @Getter
    @Setter
    private boolean withdrawed;
    @Getter
    @Setter
    private  String toAccount;
    @Getter
    @Setter
    private boolean deposited;
    @Getter
    @Setter
    private int amount;
    @Getter
    @Setter
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

    public void complete(CompleteDeposit command) {
        //
        this.deposited = true;
        this.complete();
    }

    public void complete(CompleteWithdraw command) {
        //
        this.withdrawed = true;
        this.complete();
    }

    private void complete() {
        //
        /*
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        */

        if (this.withdrawed && this.deposited) {
            this.completed = true;
        }
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
