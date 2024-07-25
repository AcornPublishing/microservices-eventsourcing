package io.cosmos.account.view;

import io.cosmos.account.event.AccountOpened;
import io.cosmos.account.event.Deposited;
import io.cosmos.account.event.Withdrawed;
import io.cosmos.account.view.store.AccountViewStore;
import io.cosmos.transfer.event.TransferCompleted;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AccountEventHandler {
    //
    private final AccountViewStore accountViewStore;

    public AccountEventHandler(AccountViewStore accountViewStore) {
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
        if (event.getTransferId() == null) {
            AccountView view = this.accountViewStore.retrieve(event.getAccountNo());
            view.setBalance(view.getBalance() + event.getAmount());
            this.accountViewStore.update(view);
        }
    }

    @EventListener
    public void on(Withdrawed event) {
        //
        if (event.getTransferId() == null) {
            AccountView view = this.accountViewStore.retrieve(event.getAccountNo());
            view.setBalance(view.getBalance() - event.getAmount());
            this.accountViewStore.update(view);
        }
    }

    @EventListener
    public void on(TransferCompleted event) {
        //
        AccountView fromAccountView = this.accountViewStore.retrieve(event.getFromAccountNo());
        fromAccountView.setBalance(fromAccountView.getBalance() - event.getAmount());
        this.accountViewStore.update(fromAccountView);

        AccountView toAccountView = this.accountViewStore.retrieve(event.getToAccountNo());
        toAccountView.setBalance(toAccountView.getBalance() + event.getAmount());
        this.accountViewStore.update(toAccountView);
    }
}
