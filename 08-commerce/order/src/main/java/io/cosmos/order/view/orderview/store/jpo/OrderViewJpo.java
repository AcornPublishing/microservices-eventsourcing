package io.cosmos.order.view.orderview.store.jpo;

import io.cosmos.order.aggregate.State;
import io.cosmos.order.view.orderview.OrderView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "TB_ORDER_VIEW")
@Getter
@Setter
@NoArgsConstructor
public class OrderViewJpo {
    //
    @Id
    private String no;
    private String userNo;
    private String representativeProductName;
    private int itemSize;
    @Enumerated(value = EnumType.STRING)
    private State state;

    public OrderViewJpo(OrderView view) {
        //
        this.no = view.getNo();
        this.userNo = view.getUserNo();
        this.representativeProductName = view.getRepresentativeProductName();
        this.itemSize = view.getItemSize();
        this.state = view.getState();
    }

    public OrderView toView() {
        //
        return new OrderView(this.no, this.userNo, this.representativeProductName, this.itemSize, this.state);
    }
}
