package io.cosmos.order.aggregate;

public enum State {
    //
    Placed,
    Completed,
    Shipping,
    Shipped,
    Delivering,
    Delivered,
    Canceled;
}
