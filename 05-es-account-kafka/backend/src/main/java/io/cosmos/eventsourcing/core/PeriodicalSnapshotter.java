package io.cosmos.eventsourcing.core;

import io.cosmos.util.JsonUtil;

import java.util.List;
import java.util.Optional;

public class PeriodicalSnapshotter implements SnapshotStrategy {
    //
    @Override
    public Optional<Snapshot> takeSnapshot(Optional<Snapshot> currentSnapshot, List<Event> events, long sequence) {
        //
        Optional<Snapshot> result = Optional.empty();

        long currentTime = System.currentTimeMillis();
        if (currentSnapshot.isEmpty()) {
            result = Optional.of(new Snapshot(JsonUtil.toJson(this), sequence, currentTime));
        }
        if (currentSnapshot.isPresent()
                && currentTime - currentSnapshot.get().getTime() > 600000) {
            result = Optional.of(new Snapshot(JsonUtil.toJson(this), sequence, currentTime));
        }

        return result;
    }
}
