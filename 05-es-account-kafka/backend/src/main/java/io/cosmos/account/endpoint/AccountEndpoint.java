package io.cosmos.account.endpoint;

import io.cosmos.account.aggregate.Account;
import io.cosmos.account.command.CloseAccount;
import io.cosmos.account.command.Deposit;
import io.cosmos.account.command.OpenAccount;
import io.cosmos.account.command.Withdraw;
import io.cosmos.account.query.QueryAccount;
import io.cosmos.account.query.QueryEvents;
import io.cosmos.account.service.AccountService;
import io.cosmos.eventsourcing.core.Event;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/account")
public class AccountEndpoint {
    //
    private final AccountService accountService;

    public AccountEndpoint(AccountService accountService) {
        //
        this.accountService = accountService;
    }

    @PostMapping(headers = { "command=OpenAccount" })
    public String openAccount(@RequestBody OpenAccount command) {
        //
        return this.accountService.openAccount(command);
    }

    @GetMapping(value = "/{accountNo}", headers = { "query=QueryAccount" })
    public Account queryAccount(@PathVariable String accountNo) {
        //
        QueryAccount query = new QueryAccount(accountNo);
        return this.accountService.queryAccount(query);
    }

    @GetMapping(value = "/{accountNo}/event", headers = { "query=QueryEvents" })
    public List<Event> queryEvents(@PathVariable String accountNo) {
        //
        QueryEvents query = new QueryEvents(accountNo);
        return this.accountService.queryEvents(query);
    }

    @PutMapping(value = "/{accountNo}", headers = { "command=Deposit" })
    public void deposit(@PathVariable String accountNo, @RequestBody Deposit command) {
        //
        command.setAccountNo(accountNo);
        this.accountService.deposit(command);
    }

    @PutMapping(value = "/{accountNo}", headers = { "command=Withdraw" })
    public void withdraw(@PathVariable String accountNo, @RequestBody Withdraw command) {
        //
        command.setAccountNo(accountNo);
        this.accountService.withdraw(command);
    }

    @DeleteMapping(value = "/{accountNo}", headers = { "command=CloseAccount" })
    public void close(@PathVariable String accountNo) {
        //
        CloseAccount command = new CloseAccount(accountNo);
        this.accountService.close(command);
    }
}
