package io.cosmos.account.store.jpa;

import io.cosmos.account.aggregate.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "TB_ACCOUNT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountJpo {
    //
    @Id
    private String no;
    private int balance;
    @Version
    private long version;

    public AccountJpo(Account account) {
        //
        this.no = account.getNo();
        this.balance = account.getBalance();
        this.version = account.version();
    }

    public Account toAccount() {
        //
        return new Account(this.no, this.balance, this.version);
    }
}
