package io.cosmos.eventsourcing.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class EventSourcedAggregate {
    //
    private List<Event> events;
    //
    private boolean inSaga;
    private boolean deleted;
    //
    private long sequence;
    private long version;
    //
    protected SnapshotStrategy snapshotStrategy;
    private Optional<Snapshot> snapshot;

    public abstract String identifier();

    public EventSourcedAggregate() {
        //
        this.events = new ArrayList<>();
    }

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
        if (this.snapshotStrategy != null) {
            this.snapshot = this.snapshotStrategy.takeSnapshot(this.snapshot, this.events, this.sequence());
        }
        /*
        long currentTime = System.currentTimeMillis();
        if (snapshot.isEmpty()) {
            this.snapshot = Optional.of(new Snapshot(JsonUtil.toJson(this), this.sequence(), currentTime));
        }
        if (snapshot.isPresent()
                && currentTime - snapshot.get().getTime() > 600000) {
            this.snapshot = Optional.of(new Snapshot(JsonUtil.toJson(this), this.sequence(), currentTime));
        }
        */
    }

    public Optional<Snapshot> snapshot() {
        //
        return this.snapshot;
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
