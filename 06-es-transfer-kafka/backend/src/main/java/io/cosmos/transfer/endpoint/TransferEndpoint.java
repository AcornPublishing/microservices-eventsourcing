package io.cosmos.transfer.endpoint;

import io.cosmos.transfer.aggregate.Transfer;
import io.cosmos.transfer.command.TransferMoney;
import io.cosmos.transfer.query.QueryComplete;
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

    @PostMapping(headers = { "command=TransferMoney" })
    public String transferMoney(@RequestBody TransferMoney command) {
        //
        return this.transferService.transferMoney(command);
    }

    @GetMapping(value = "/{transferId}", headers = { "query=QueryTransfer" })
    public Transfer queryTransfer(@PathVariable String transferId) {
        //
        QueryTransfer query = new QueryTransfer(transferId);
        return this.transferService.queryTransfer(query);
    }
}
