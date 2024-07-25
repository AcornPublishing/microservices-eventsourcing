package io.cosmos.saga.endpoint;

import io.cosmos.saga.core.EventSourcedSaga;
import io.cosmos.saga.store.SagaStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/saga")
public class SagaEndpoint {
    //
    private final SagaStore sagaSagaStore;

    public SagaEndpoint(SagaStore sagaSagaStore) {
        //
        this.sagaSagaStore = sagaSagaStore;
    }

    @GetMapping
    public List<String> querySagas() {
        //
        return this.sagaSagaStore.loadAll();
    }

    @GetMapping(value = "/{sagaId}")
    public EventSourcedSaga querySaga(@PathVariable String sagaId) {
        //
        return this.sagaSagaStore.load(sagaId);
    }
}
