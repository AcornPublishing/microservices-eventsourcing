package io.cosmos.eventsourcing.core;

import io.cosmos.util.JsonUtil;

import java.util.UUID;

public abstract class Event {
    //
    private String eventId;
    //
    private long sequence;
    private long time;
    //
    private String correlationId;
    //
    private boolean relayed;
    private boolean propagate = true;

    protected Event() {
        //
        this.eventId = UUID.randomUUID().toString();
        this.time = System.currentTimeMillis();
    }

    public String eventId() {
        //
        return this.eventId;
    }

    public void eventId(String eventId) {
        //
        this.eventId = eventId;
    }

    public long sequence() {
        //
        return this.sequence;
    }

    public void sequence(long sequence) {
        //
        this.sequence = sequence;
    }

    public long time() {
        //
        return this.time;
    }

    public void time(long time) {
        //
        this.time = time;
    }

    public String correlationId() {
        //
        return this.correlationId;
    }

    public void correlationId(String correlationId) {
        //
        this.correlationId = correlationId;
    }

    public String payload() {
        //
        return JsonUtil.toJson(this);
    }

    public String typeName() {
        //
        return this.getClass().getTypeName();
    }

    public boolean propagate() {
        //
        return this.propagate;
    }

    public void propagate(boolean propagate) {
        //
        this.propagate = propagate;
    }

    public boolean relayed() {
        //
        return this.relayed;
    }

    public void relayed(boolean relayed) {
        //
        this.relayed = relayed;
    }
}
