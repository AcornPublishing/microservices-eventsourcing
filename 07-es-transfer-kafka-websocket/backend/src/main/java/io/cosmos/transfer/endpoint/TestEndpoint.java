package io.cosmos.transfer.endpoint;

import io.cosmos.transfer.event.TransferCompleted;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestEndpoint {
    //
    private final ApplicationEventPublisher applicationEventPublisher;

    public TestEndpoint(ApplicationEventPublisher applicationEventPublisher) {
        //
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @PostMapping(value = "/{transferId}")
    public void test(@PathVariable String transferId) {
        //
        this.applicationEventPublisher.publishEvent(new TransferCompleted(transferId));
    }
}
