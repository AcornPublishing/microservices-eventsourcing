package io.cosmos.transfer.service;

import io.cosmos.core.Gateway;
import io.cosmos.transfer.aggregate.Transfer;
import io.cosmos.transfer.command.CancelTransfer;
import io.cosmos.transfer.command.CompleteDeposit;
import io.cosmos.transfer.command.CompleteWithdraw;
import io.cosmos.transfer.command.TransferMoney;
import io.cosmos.transfer.event.TransferCreated;
import io.cosmos.transfer.event.TransferCanceled;
import io.cosmos.transfer.query.QueryTransfer;
import io.cosmos.transfer.store.TransferStore;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransferService {
    //
    private final TransferStore transferStore;
    private final Gateway gateway;

    public TransferService(TransferStore transferStore,
                           Gateway gateway) {
        //
        this.transferStore = transferStore;
        this.gateway = gateway;
    }

    public String transfer(TransferMoney command) {
        //
        String newId = UUID.randomUUID().toString().split("-")[0];
        command.setTransferId(newId);
        Transfer transfer = new Transfer(command);
        this.transferStore.create(transfer);

        this.gateway.publish(new TransferCreated(newId,
                command.getFromAccount(), command.getToAccount(),
                command.getAmount()));

        return newId;
    }

    public Transfer query(QueryTransfer query) {
        //
        return this.transferStore.retrieve(query.getTransferId());
    }

    public void complete(CompleteDeposit command) {
        //
        System.out.println("TransferService.complete(CompleteDeposit)");
        Transfer transfer = this.transferStore.retrieve(command.getTransferId());
        transfer.complete(command);
        this.transferStore.update(transfer);
    }

    public void complete(CompleteWithdraw command) {
        //
        System.out.println("TransferService.complete(CompleteWithdraw)");
        Transfer transfer = this.transferStore.retrieve(command.getTransferId());
        transfer.complete(command);
        this.transferStore.update(transfer);
    }

    public void cancel(CancelTransfer command) {
        //
        System.out.println("TransferService.complete(CompleteWithdraw)");
        Transfer transfer = this.transferStore.retrieve(command.getTransferId());
        transfer.cancel(command);
        this.transferStore.update(transfer);

        this.gateway.publish(new TransferCanceled(transfer.getTransferId(),
                transfer.getFromAccount(), transfer.getToAccount(),
                transfer.getAmount()));
    }
}
