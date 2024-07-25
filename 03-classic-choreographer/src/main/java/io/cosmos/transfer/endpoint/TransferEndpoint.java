package io.cosmos.transfer.endpoint;

import io.cosmos.transfer.aggregate.Transfer;
import io.cosmos.transfer.command.TransferMoney;
import io.cosmos.transfer.query.QueryTransfer;
import io.cosmos.transfer.service.TransferService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/transfer")
public class TransferEndpoint {
    //
    private final TransferService transferService;

    public TransferEndpoint(TransferService transferService) {
        //
        this.transferService = transferService;
    }

    @PostMapping
    public String transfer(@RequestBody TransferMoney command) {
        //
        return this.transferService.transfer(command);
    }

    @GetMapping(value = "/{transferId}")
    public Transfer queryTransfer(@PathVariable String transferId) {
        //
        QueryTransfer query = new QueryTransfer(transferId);
        return this.transferService.query(query);
    }
}
