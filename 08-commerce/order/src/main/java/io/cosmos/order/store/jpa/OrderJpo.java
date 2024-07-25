package io.cosmos.order.store.jpa;

import io.cosmos.eventsourcing.Snapshot;
import io.cosmos.order.aggregate.Order;
import io.cosmos.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "TB_ORDER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderJpo {
    //
    @Id
    private String no;
    //
    private boolean deleted;
    //
    private long sequence;
    @Version
    private long version;
    //
    @Lob
    @Column(name = "SNAPSHOT_PAYLOAD")
    private String snapshotPayload;
    @Column(name = "SNAPSHOT_TIME")
    private long snapshotTime;

    public OrderJpo(Order order) {
        //
        this.no = order.getNo();
        //
        this.deleted = order.deleted();
        //
        this.sequence = order.sequence();
        this.version = order.getVersion();
        //
        if (order.snapshot().isPresent()) {
            System.out.println(order.getNo() + ": true");
            this.snapshotPayload = order.snapshot().get().getPayload();
            this.snapshotTime = order.snapshot().get().getTime();
        }
    }

    public Order toOrder() {
        //
        Order order = null;

        if (this.snapshotTime > 0) {
            order = JsonUtil.fromJson(this.snapshotPayload, Order.class);
            order.snapshot(new Snapshot(this.snapshotPayload, this.snapshotTime));
            order.sequence(this.sequence);
            return order;
        }
        order = JsonUtil.fromJson("{}", Order.class);
        return order;
    }

}
