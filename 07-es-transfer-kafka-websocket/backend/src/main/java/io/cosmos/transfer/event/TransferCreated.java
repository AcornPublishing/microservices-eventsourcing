package io.cosmos.transfer.event;

import io.cosmos.eventsourcing.core.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferCreated extends Event {
    //
    private String transferId;
    private String fromAccountNo;
    private String toAccountNo;
    private int amount;
}
