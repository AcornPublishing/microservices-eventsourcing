package io.cosmos.eventsourcing;

import java.util.ArrayList;
import java.util.List;

public abstract class Upcaster {
    //
    private List<Caster> casters;

    public Upcaster() {
        //
        this.casters = new ArrayList<>();
        this.initialize();
    }

    public abstract void initialize();
    public abstract boolean canUpcast(IntermediateEvent event);

    protected void addCaster(Caster caster) {
        //
        this.casters.add(caster);
    }

    public IntermediateEvent upcast(IntermediateEvent
                                            intermediateEvent) {
        IntermediateEvent intermediate = intermediateEvent;

        for (Caster caster: casters) {
            intermediate = caster.cast(intermediate);
        }

        return intermediate;
    }

}
