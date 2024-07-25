package io.cosmos.account.endpoint;

import io.cosmos.account.aggregate.Account;
import io.cosmos.account.command.CreateAccount;
import io.cosmos.account.command.Deposit;
import io.cosmos.account.command.Withdraw;
import io.cosmos.account.query.QueryAccount;
import io.cosmos.account.service.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/account")
public class AccountEndpoint {
    //
    private final AccountService accountService;

    public AccountEndpoint(AccountService accountService) {
        //
        this.accountService = accountService;
    }

    @PostMapping
    public String createAccount(@RequestBody CreateAccount command) {
        //
        return this.accountService.createAccount(command);
    }

    @GetMapping(value = "/{no}")
    public Account queryAccount(@PathVariable String no) {
        //
        QueryAccount query = new QueryAccount(no);
        return this.accountService.queryAccount(query);
    }

    @PutMapping(value = "/{no}", headers = {"command=Deposit"} )
    public void deposit(@PathVariable String no, @RequestBody Deposit command) {
        //
        command.setNo(no);
        this.accountService.deposit(command);
    }

    @PutMapping(value = "/{no}", headers = {"command=Withdraw"} )
    public void deposit(@PathVariable String no, @RequestBody Withdraw command) {
        //
        command.setNo(no);
        this.accountService.withdraw(command);
    }
}
