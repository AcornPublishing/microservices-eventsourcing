package io.cosmos.eventsourcing;

public interface Caster {
    boolean canUpcast(IntermediateEvent event);
    public IntermediateEvent cast(IntermediateEvent event);
}

