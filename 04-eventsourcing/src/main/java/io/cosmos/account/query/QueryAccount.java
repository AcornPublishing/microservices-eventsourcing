package io.cosmos.account.query;

import io.cosmos.eventsourcing.core.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QueryAccount extends Query {
    //
    private String accountNo;
}
