package io.cosmos.account.view.handler;

import io.cosmos.account.event.*;
import io.cosmos.account.view.AccountView;
import io.cosmos.account.view.handler.store.AccountViewStore;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AccountViewHandler {
    //
    private final AccountViewStore accountViewStore;

    public AccountViewHandler(AccountViewStore accountViewStore) {
        //
        this.accountViewStore = accountViewStore;
    }

    @EventListener
    public void on(AccountOpened event) {
        //
        AccountView view = new AccountView(event.getAccountNo(), event.getBalance());
        this.accountViewStore.create(view);
    }

    @EventListener
    public void on(Deposited event) {
        //
        if (event.getTransferId() != null) {
            return;
        }

        AccountView view = this.accountViewStore.retrieve(event.getAccountNo());
        view.setBalance(view.getBalance() + event.getAmount());
        this.accountViewStore.update(view);
    }

    @EventListener
    public void on(DepositCompleted event) {
        //
        AccountView view = this.accountViewStore.retrieve(event.getAccountNo());
        view.setBalance(view.getBalance() + event.getAmount());
        this.accountViewStore.update(view);
    }

    @EventListener
    public void on(Withdrawed event) {
        //
        if (event.getTransferId() != null) {
            return;
        }

        AccountView view = this.accountViewStore.retrieve(event.getAccountNo());
        view.setBalance(view.getBalance() - event.getAmount());
        this.accountViewStore.update(view);
    }

    @EventListener
    public void on(WithdrawCompleted event) {
        //
        AccountView view = this.accountViewStore.retrieve(event.getAccountNo());
        view.setBalance(view.getBalance() - event.getAmount());
        this.accountViewStore.update(view);
    }
}
