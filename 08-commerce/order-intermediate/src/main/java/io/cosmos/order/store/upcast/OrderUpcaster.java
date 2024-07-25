package io.cosmos.order.store.upcast;

import io.cosmos.eventsourcing.Caster;
import io.cosmos.eventsourcing.IntermediateEvent;
import io.cosmos.eventsourcing.Upcaster;
import io.cosmos.order.aggregate.CancelReason;
import io.cosmos.order.event.OrderCanceled;
import org.springframework.stereotype.Component;

@Component
public class OrderUpcaster extends Upcaster {

    private static final String typeName = OrderCanceled.class.getName();

    @Override
    public void initialize() {
        this.addCaster(new WithReason());
    }

    @Override
    public boolean canUpcast(IntermediateEvent event) {
        //
        return typeName.equals(event.getTypeName());
    }

    public class WithReason implements Caster {

        private static final String sourceRevision = "1";
        private static final String targetRevision = "2";

        @Override
        public boolean canUpcast(IntermediateEvent event) {
            //
            if (sourceRevision.equals(event.getRevision())
                    && typeName.equals(event.getTypeName())) {
                return true;
            }

            return false;
        }

        @Override
        public IntermediateEvent cast(IntermediateEvent event) {
            if (this.canUpcast(event)) {
                event.getPayload().put("reason", CancelReason.Etc.toString());
                event.setRevision(targetRevision);
            }
            return event;
        }
    }
}
