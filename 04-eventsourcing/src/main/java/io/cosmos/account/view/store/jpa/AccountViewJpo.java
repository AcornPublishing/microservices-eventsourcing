package io.cosmos.account.view.store.jpa;

import io.cosmos.account.view.AccountView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_ACCOUNT_VIEW")
@Getter
@Setter
@NoArgsConstructor
public class AccountViewJpo {
    //
    @Id
    private String accountNo;
    private int balance;

    public AccountViewJpo(AccountView view) {
        //
        this.accountNo = view.getAccountNo();
        this.balance = view.getBalance();
    }

    public AccountView toView() {
        //
        return new AccountView(this.accountNo, this.balance);
    }
}
