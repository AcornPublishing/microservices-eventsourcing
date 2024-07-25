package io.cosmos.account.event;

import io.cosmos.eventsourcing.core.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawFailed extends Event {
    //
    private int balance;
    private int amount;
    //
    private String transferId;
}