package io.cosmos.account.command;

import io.cosmos.eventsourcing.core.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompleteWithdraw extends Event {
    //
    private String accountNo;
    private int amount;
    //
    private String transferId;
}
