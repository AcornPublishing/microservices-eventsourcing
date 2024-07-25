package io.cosmos.transfer.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferMoney {
    //
    transient private String transferId;
    //
    private String fromAccount;
    private  String toAccount;
    private int amount;
}
