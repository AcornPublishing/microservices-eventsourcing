package io.cosmos.cart.command;

import io.cosmos.eventsourcing.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveCart extends Command {
    //
    transient private String cartId;
}
