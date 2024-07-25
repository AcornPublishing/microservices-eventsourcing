package io.cosmos.order.command;

import io.cosmos.eventsourcing.Command;
import io.cosmos.order.aggregate.CancelReason;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CancelOrder extends Command {
    //
    private String no;
    private CancelReason reason;
}
