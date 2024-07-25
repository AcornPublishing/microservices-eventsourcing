package io.cosmos.order.aggregate;

import io.cosmos.eventsourcing.Event;
import io.cosmos.eventsourcing.EventHandlerInvokeException;
import io.cosmos.eventsourcing.EventHandlerNotFoundException;
import io.cosmos.eventsourcing.Snapshot;
import io.cosmos.order.command.CancelOrder;
import io.cosmos.order.command.CompleteOrder;
import io.cosmos.order.command.PlaceOrder;
import io.cosmos.order.command.Ship;
import io.cosmos.order.event.*;
import io.cosmos.util.JsonUtil;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Order {
    //
    @Getter
    private String no;
    @Getter
    private List<Item> items;
    @Getter
    private ShippingAddress shippingAddress;
    @Getter
    private Orderer orderer;
    @Getter
    private State state;
    @Getter
    private CancelReason reason;

    private Order() {
        //
        this.events = new ArrayList<>();
        this.items = new ArrayList<>();
        this.snapshot = Optional.empty();
    }

    public Order(PlaceOrder command) {
        //
        this();
        this.apply(new OrderPlaced(command.getNo(), command.getItems(), command.getShippingAddress(), command.getOrderer()));
    }

    private void on(OrderPlaced event) {
        //
        this.no = event.getNo();
        this.items = event.getItems();
        this.shippingAddress = event.getShippingAddress();
        this.orderer = event.getOrderer();
        //
        this.state = State.Placed;
        //
        this.startSaga();
    }

    public void complete(CompleteOrder command) {
        //
        this.apply(new OrderCompleted(command.getNo()));
    }

    private void on(OrderCompleted event) {
        //
        this.state = State.Completed;
        //
        this.endSaga();
    }

    public void cancel(CancelOrder command) {
        //
        if (!this.inSaga()) {
            return;
        }

        if (this.state != State.Placed && this.state != State.Completed) {
            throw new IllegalStateException("State is not Placed or Completed.");
        }
        this.apply(new OrderCanceledWithReason(this.no, command.getReason()));
    }

    private void on(OrderCanceledWithReason event) {
        //
        this.state = State.Canceled;
        this.reason = event.getReason();
        //
        this.endSaga();
    }

    public void ship(Ship command) {
        //
        this.apply(new OrderDeliveryBegan(command.getNo()));
    }

    private void on(OrderDeliveryBegan event) {
        //
        this.state = State.Delivering;
    }

    //
    private List<Event> events;
    //
    private boolean inSaga;
    private boolean deleted;
    //
    private long sequence;
    private long version;
    //
    private Optional<Snapshot> snapshot;

    public void apply(Event event) {
        //
        this.apply(event, true);
    }

    public void apply(Event event, boolean isNew) {
        //
        try {
            Method eventHandler = this.getClass().getDeclaredMethod("on", event.getClass());
            eventHandler.setAccessible(true);
            eventHandler.invoke(this, event);
            if (isNew) {
                event.sequence(++sequence);
                this.events.add(event);
            } else {
                this.sequence(event.sequence());
            }
        } catch (NoSuchMethodException e) {
            throw new EventHandlerNotFoundException(this.getClass(), event.getClass());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new EventHandlerInvokeException(this.getClass(), event.getClass());
        }
    }

    public void takeSnapshot() {
        //
        long currentTime = System.currentTimeMillis();

        if (this.hasSnapshotEvent()) {
            System.out.println("create snapshot");
            this.snapshot = Optional.of(new Snapshot(JsonUtil.toJson(this), currentTime));
        }
    }

    private boolean hasSnapshotEvent() {
        //
        return this.events.stream().filter(event -> { return OrderCompleted.class.isAssignableFrom(event.getClass()); }).findFirst().isPresent();
    }

    public Optional<Snapshot> snapshot() {
        //
        return this.snapshot;
    }

    public void snapshot(Snapshot snapshot) {
        //
        this.snapshot = Optional.of(snapshot);
    }

    public List<Event> events() {
        //
        return this.events;
    }

    public long sequence() {
        //
        return this.sequence;
    }

    public void sequence(long sequence) {
        //
        this.sequence = sequence;
    }

    public long getVersion() {
        //
        return this.version;
    }

    public void setVersion(long version) {
        //
        this.version = version;
    }

    public void markDelete() {
        //
        this.deleted = true;
    }

    public boolean deleted() {
        //
        return this.deleted;
    }

    public boolean inSaga() {
        //
        return this.inSaga;
    }

    protected void startSaga() {
        //
        this.inSaga = true;
    }

    protected void endSaga() {
        //
        this.inSaga = false;
    }
}
