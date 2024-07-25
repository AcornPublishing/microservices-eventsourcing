package io.cosmos.account.command;

import io.cosmos.eventsourcing.core.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Deposit extends Command {
    //
    transient private String accountNo;
    private int amount;
    //
    private String transferId;
}
