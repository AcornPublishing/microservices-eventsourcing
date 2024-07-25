package io.cosmos.account.command;

import io.cosmos.eventsourcing.core.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompleteDeposit extends Command {
    //
    private String accountNo;
    private int amount;
    //
    private String transferId;
}
