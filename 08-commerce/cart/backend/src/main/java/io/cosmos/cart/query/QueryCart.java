package io.cosmos.cart.query;

import io.cosmos.eventsourcing.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QueryCart extends Query {
    //
    private String cartId;
}
