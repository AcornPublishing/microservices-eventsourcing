package io.cosmos.eventsourcing.core;

import io.cosmos.util.JsonUtil;

import java.util.List;
import java.util.Optional;

public class EventSnapshotter implements SnapshotStrategy {
    //
    private List<Event> events;

    public EventSnapshotter(List<Event> events) {
        //
        this.events = events;
    }

    @Override
    public Optional<Snapshot> takeSnapshot(Optional<Snapshot> currentSnapshot, List<Event> events, long sequence) {
        //
        Optional<Snapshot> result = Optional.empty();

        for (Event snapshotEvent: this.events) {
            for (Event currentEvent: events) {
                if (snapshotEvent.getClass().isAssignableFrom(currentEvent.getClass())) {
                    long currentTime = System.currentTimeMillis();
                    result = Optional.of(new Snapshot(JsonUtil.toJson(this), sequence, currentTime));
                    break;
                }
            }
        }

        return result;
    }
}
