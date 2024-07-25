package io.cosmos.order.view.orderview;

import io.cosmos.order.aggregate.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderView {
    //
    private String no;
    private String userNo;
    private String representativeProductName;
    private int itemSize;
    private State state;

    public void cancel() {
        //
        this.state = State.Canceled;
    }

    public void ship() {
        //
        this.state = State.Shipped;
    }
}
