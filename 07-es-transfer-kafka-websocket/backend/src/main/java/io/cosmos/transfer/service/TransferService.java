package io.cosmos.transfer.service;

import io.cosmos.eventsourcing.store.AggregateStore;
import io.cosmos.transfer.aggregate.State;
import io.cosmos.transfer.aggregate.Transfer;
import io.cosmos.transfer.command.CancelTransfer;
import io.cosmos.transfer.command.CompleteTransfer;
import io.cosmos.transfer.command.TransferMoney;
import io.cosmos.transfer.query.QueryComplete;
import io.cosmos.transfer.query.QueryTransfer;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransferService {
    //
    private final AggregateStore<Transfer> aggregateStore;

    public TransferService(AggregateStore<Transfer> aggregateStore) {
        //
        this.aggregateStore = aggregateStore;
    }

    public String transferMoney(TransferMoney command) {
        //
        String newTransferId = UUID.randomUUID().toString().split("-")[0];
        command.setTransferId(newTransferId);

        Transfer transfer = new Transfer(command);
        this.aggregateStore.save(transfer);

        return newTransferId;
    }

    public Transfer queryTransfer(QueryTransfer query) {
        //
        return this.aggregateStore.load(query.getTransferId());
    }

    public void complete(CompleteTransfer command) {
        //
        Transfer transfer = this.aggregateStore.load(command.getTransferId());
        transfer.completeTransfer(command);
        this.aggregateStore.save(transfer);
    }

    public void cancel(CancelTransfer command) {
        //
        Transfer transfer = this.aggregateStore.load(command.getTransferId());
        transfer.cancelTransfer(command);
        this.aggregateStore.save(transfer);
    }
}
