package io.cosmos.account.event;

import io.cosmos.eventsourcing.core.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DepositCanceled extends Event {
}
