package io.cosmos.cart.aggregate;

import io.cosmos.cart.command.*;
import io.cosmos.cart.event.*;
import io.cosmos.eventsourcing.Event;
import io.cosmos.eventsourcing.EventHandlerInvokeException;
import io.cosmos.eventsourcing.EventHandlerNotFoundException;
import io.cosmos.eventsourcing.Snapshot;
import io.cosmos.util.JsonUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cart {
    //
    private String cartId;
    private List<Item> items;

    public String getCartId() {
        //
        return this.cartId;
    }

    private Cart() {
        //
        this.events = new ArrayList<>();
        this.items = new ArrayList<>();
        this.snapshot = Optional.empty();
    }

    public Cart(CreateCart command) {
        //
        this();
        this.apply(new CartCreated(command.getCartId()));
    }

    private void on(CartCreated event) {
        //
        this.cartId = event.getCartId();
    }

    public void addItem(AddItem command) {
        //
        Optional<Item> foundItem = this.findItem(command.getProductNo());
        if (foundItem.isPresent()) {
            return;
        }

        this.apply(new ItemAdded(command.getCartId(), command.getProductNo(), command.getProductName(), command.getPrice(), command.getQuantity()));
    }

    private void on(ItemAdded event) {
        //
        this.items.add(new Item(new Product(event.getProductNo(), event.getProductName(), event.getPrice()), event.getQuantity()));
    }

    public void changeQuantity(ChangeQuantity command) {
        //
        Optional<Item> foundItem = this.findItem(command.getProductNo());
        if (foundItem.isPresent()) {
            if (command.getQuantity() > 0) {
                this.apply(new QuantityChanged(this.getCartId(), command.getProductNo(), command.getQuantity()));
            } else {
                this.apply(new ItemRemoved(this.getCartId(), command.getProductNo()));
            }
        }
    }

    private void on(QuantityChanged event) {
        //
        Optional<Item> foundItem = this.findItem(event.getProductNo());
        if (foundItem.isPresent()) {
            foundItem.get().changeQuantity(event.getQuantity());
        }
    }

    public void removeItem(RemoveItem command) {
        //
        Optional<Item> foundItem = this.findItem(command.getProductNo());
        if (foundItem.isPresent()) {
            this.apply(new ItemRemoved(this.getCartId(), command.getProductNo()));
        }
    }

    private void on(ItemRemoved event) {
        //
        Optional<Item> foundItem = this.findItem(event.getProductNo());
        this.items.remove(foundItem.get());
    }

    public void remove(RemoveCart command) {
        //
        this.apply(new CartRemoved(command.getCartId()));
    }

    private void on(CartRemoved event) {
        //
        this.items.clear();
        this.markDelete();
    }

    private Optional<Item> findItem(String productNo) {
        //
        return this.items.stream().filter(item -> { return productNo.equals(item.getProduct().getNo()); }).findFirst();
    }

    public List<Item> getItems() {
        //
        return items;
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
        if (snapshot.isEmpty()) {
            this.snapshot = Optional.of(new Snapshot(JsonUtil.toJson(this), currentTime, this.sequence));
        }
        if (snapshot.isPresent() &&
                snapshot.get().isExpired(this.sequence)) {
            this.snapshot = Optional.of(new Snapshot(JsonUtil.toJson(this), currentTime, this.sequence));
        }

        /*
        long currentTime = System.currentTimeMillis();
        if (snapshot.isEmpty()) {
            this.snapshot = Optional.of(new Snapshot(JsonUtil.toJson(this), currentTime));
        }
        if (snapshot.isPresent()
                && currentTime - snapshot.get().getTime() > 600000) {
            this.snapshot = Optional.of(new Snapshot(JsonUtil.toJson(this), currentTime));
        }
        */
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
