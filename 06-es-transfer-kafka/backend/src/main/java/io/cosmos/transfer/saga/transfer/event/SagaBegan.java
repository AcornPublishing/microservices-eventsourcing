package io.cosmos.transfer.saga.transfer.event;

import io.cosmos.eventsourcing.core.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SagaBegan extends Event {
    //
    private String transferId;
    private String fromAccountNo;
    private String toAccountNo;
    private int amount;
}
