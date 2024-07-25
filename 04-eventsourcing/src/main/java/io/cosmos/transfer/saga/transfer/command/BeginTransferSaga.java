package io.cosmos.transfer.saga.transfer.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BeginTransferSaga {
    //
    private String transferId;
    private String fromAccountNo;
    private String toAccountNo;
    private int amount;
}
