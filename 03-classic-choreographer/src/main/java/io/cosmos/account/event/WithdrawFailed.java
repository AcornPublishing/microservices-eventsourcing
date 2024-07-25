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
public class WithdrawFailed extends Event {
    //
    private Optional<String> transferId;
}
