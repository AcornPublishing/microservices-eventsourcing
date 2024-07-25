package io.cosmos.transfer.query;

import io.cosmos.eventsourcing.core.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QueryTransfer extends Query {
    //
    private String transferId;
}
