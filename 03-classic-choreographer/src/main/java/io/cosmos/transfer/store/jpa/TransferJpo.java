package io.cosmos.transfer.store.jpa;

import io.cosmos.transfer.aggregate.Transfer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "TB_TRANSFER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferJpo {
    //
    @Id
    private String transferId;
    //
    private String fromAccount;
    private  String toAccount;
    private int amount;
    //
    private boolean completed;
    //
    @Version
    private long version;

    public TransferJpo(Transfer transfer) {
        //
        this.transferId = transfer.getTransferId();
        this.fromAccount = transfer.getFromAccount();
        this.toAccount = transfer.getToAccount();
        this.amount = transfer.getAmount();
        this.completed = transfer.isCompleted();
        //
        this.version = transfer.version();
    }

    public Transfer toTransfer() {
        //
        return new Transfer(this.transferId,
                this.fromAccount,
                this.toAccount,
                this.amount,
                this.completed,
                this.version);
    }
}
