package io.cosmos.eventsourcing.core;

import java.util.List;
import java.util.Optional;

public interface SnapshotStrategy {
    //
    public Optional<Snapshot> takeSnapshot(Optional<Snapshot> currentSnapshot, List<Event> events, long sequence);
}
