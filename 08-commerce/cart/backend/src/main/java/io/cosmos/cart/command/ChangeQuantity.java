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
public class ChangeQuantity extends Command {
    //
    transient private String cartId;
    //
    private String productNo;
    private int quantity;
}
