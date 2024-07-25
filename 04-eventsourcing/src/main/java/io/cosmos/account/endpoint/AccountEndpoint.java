package io.cosmos.account.endpoint;

import io.cosmos.account.aggregate.Account;
import io.cosmos.account.command.CloseAccount;
import io.cosmos.account.command.Deposit;
import io.cosmos.account.command.OpenAccount;
import io.cosmos.account.command.Withdraw;
import io.cosmos.account.query.QueryAccount;
import io.cosmos.account.service.AccountService;
import io.cosmos.account.view.store.AccountViewStore;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/account")
public class AccountEndpoint {
    //
    private final AccountService accountService;
    private final AccountViewStore accountViewStore;

    public AccountEndpoint(AccountService accountService,
                           AccountViewStore accountViewStore) {
        //
        this.accountService = accountService;
        this.accountViewStore = accountViewStore;
    }

    @PostMapping
    public String openAccount(@RequestBody OpenAccount command) {
        //
        return this.accountService.openAccount(command);
    }

    @GetMapping(value = "/{accountNo}")
    public Account queryAccount(@PathVariable String accountNo) {
        //
        QueryAccount query = new QueryAccount(accountNo);
        return this.accountService.queryAccount(query);
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

    @DeleteMapping(value = "/{accountNo}")
    public void close(@PathVariable String accountNo) {
        //
        CloseAccount command = new CloseAccount(accountNo);
        this.accountService.close(command);
    }
}
