package io.cosmos.order.command;

import io.cosmos.eventsourcing.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompleteOrder extends Command {
    //
    private String no;
}
