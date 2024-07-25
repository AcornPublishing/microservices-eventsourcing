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
public class SagaDeposited extends Event {
    //
    private String transferId;
}
