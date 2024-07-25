package io.cosmos.transfer.event;

import io.cosmos.core.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferCanceled extends Event {
    //
    private String transferId;
    //
    private String fromAccount;
    private String toAccount;
    private int amount;
}
