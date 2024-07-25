package io.cosmos.order.aggregate;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Orderer {
    //
    private String no;
    private String name;

    public Orderer(String no, String name) {
        //
        this.no = no;
        this.name = name;
    }
}
