package io.cosmos.eventsourcing.core;

import io.cosmos.util.JsonUtil;

import java.util.List;
import java.util.Optional;

public class NTimesSnapshotter implements SnapshotStrategy {
    //
    private long times;

    public NTimesSnapshotter(long times) {
        //
        this.times = times;
    }

    @Override
    public Optional<Snapshot> takeSnapshot(Optional<Snapshot> currentSnapshot, List<Event> events, long sequence) {
        //
        Optional<Snapshot> result = Optional.empty();

        if (currentSnapshot.isPresent()) {
            long quotient = currentSnapshot.get().getSequence() / 3;

            if (sequence % 3 == 0 && quotient != sequence / 3) {
                long currentTime = System.currentTimeMillis();
                result = Optional.of(new Snapshot(JsonUtil.toJson(this), sequence, currentTime));
            }
        }
        if (currentSnapshot.isEmpty()) {
            if (sequence % 3 == 0) {
                long currentTime = System.currentTimeMillis();
                result = Optional.of(new Snapshot(JsonUtil.toJson(this), sequence, currentTime));
            }
        }

        return result;
    }
}
