package io.cosmos.transfer.command;

import io.cosmos.eventsourcing.core.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferMoney extends Command {
    //
    transient private String transferId;
    private String fromAccountNo;
    private String toAccountNo;
    private int amount;
}
