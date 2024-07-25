package io.cosmos.saga.core;

import io.cosmos.eventsourcing.core.Event;
import io.cosmos.eventsourcing.core.EventHandlerInvokeException;
import io.cosmos.eventsourcing.core.EventHandlerNotFoundException;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class EventSourcedSaga {
    //
    private List<Event> events;
    //
    private long sequence;
    private long version;
    private boolean inSaga;

    public abstract String identifier();

    public EventSourcedSaga() {
        //
        this.events = new ArrayList<>();
    }

    public abstract boolean completed();

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

    public boolean inSaga() {
        //
        return this.inSaga;
    }

    public void inSaga(boolean inSaga) {
        //
        this.inSaga = inSaga;
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
