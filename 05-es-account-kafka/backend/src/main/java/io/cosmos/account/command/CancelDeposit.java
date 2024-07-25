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
public class CancelDeposit extends Command {
    //
    private String accountNo;
    //
    private String transferId;

}
