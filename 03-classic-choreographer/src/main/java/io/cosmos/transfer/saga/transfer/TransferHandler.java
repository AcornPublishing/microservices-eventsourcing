package io.cosmos.transfer.saga.transfer;

import io.cosmos.account.event.WithdrawFailed;
import io.cosmos.account.event.Withdrawed;
import io.cosmos.transfer.command.CancelTransfer;
import io.cosmos.transfer.command.CompleteTransfer;
import io.cosmos.transfer.service.TransferService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TransferHandler {
    //
    private final TransferService transferService;

    public TransferHandler(TransferService transferService) {
        //
        this.transferService = transferService;
    }

    @EventListener
    public void on(Withdrawed event) {
        //
        if (event.getTransferId().isPresent()) {
            CompleteTransfer command = new CompleteTransfer(event.getTransferId().get());
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
}
