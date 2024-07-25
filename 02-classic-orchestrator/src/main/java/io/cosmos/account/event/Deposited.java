package io.cosmos.account.event;

import io.cosmos.core.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Deposited extends Event {
    //
    private String accountNo;
    private int amount;
    //
    private Optional<String> transferId;
}
