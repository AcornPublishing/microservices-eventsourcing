package io.cosmos.order.query;

import io.cosmos.eventsourcing.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QueryOrder extends Query {
    //
    private String no;
}