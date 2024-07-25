package io.cosmos.transfer.saga.transfer;

import io.cosmos.account.command.CancelDeposit;
import io.cosmos.account.command.Deposit;
import io.cosmos.account.command.Withdraw;
import io.cosmos.account.event.Deposited;
import io.cosmos.account.event.WithdrawFailed;
import io.cosmos.account.event.Withdrawed;
import io.cosmos.core.Gateway;
import io.cosmos.transfer.aggregate.Transfer;
import io.cosmos.transfer.command.CancelTransfer;
import io.cosmos.transfer.command.CompleteDeposit;
import io.cosmos.transfer.command.CompleteWithdraw;
import io.cosmos.transfer.event.TransferCreated;
import io.cosmos.transfer.event.TransferCanceled;
import io.cosmos.transfer.query.QueryTransfer;
import io.cosmos.transfer.service.TransferService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TransferOrchestrator {
    //
    private final TransferService transferService;
    private final Gateway gateway;

    public TransferOrchestrator(TransferService transferService,
                                Gateway gateway) {
        //
        this.transferService = transferService;
        this.gateway = gateway;
    }

    @EventListener
    public void on(TransferCreated event) {
        //
        Deposit deposit = new Deposit(event.getToAccount(), event.getAmount(), Optional.of(event.getTransferId()));
        this.gateway.send(deposit);
    }

    @EventListener
    public void on(Deposited event) {
        //
        if (event.getTransferId().isPresent()) {
            CompleteDeposit command = new CompleteDeposit(event.getTransferId().get());
            this.transferService.complete(command);

            QueryTransfer query = new QueryTransfer(event.getTransferId().get());
            Transfer transfer = this.transferService.query(query);

            Withdraw withdraw = new Withdraw(transfer.getFromAccount(), event.getAmount(), event.getTransferId());
            this.gateway.send(withdraw);
        }
    }

    @EventListener
    public void on(Withdrawed event) {
        //
        if (event.getTransferId().isPresent()) {
            CompleteWithdraw command = new CompleteWithdraw(event.getTransferId().get());
            this.transferService.complete(command);
        }
    }

    @EventListener
    public void on(WithdrawFailed event) {
        //
        if (event.getTransferId().isPresent()) {
            CancelTransfer command = new CancelTransfer(event.getTransferId().get());
            this.transferService.cancel(command);
        }
    }

    @EventListener
    public void on(TransferCanceled event) {
        //
        CancelDeposit command = new CancelDeposit(event.getToAccount(), event.getAmount(), Optional.of(event.getTransferId()));
        this.gateway.send(command);
    }
}
