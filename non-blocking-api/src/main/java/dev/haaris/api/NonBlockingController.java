package dev.haaris.api;

import dev.haaris.model.CustomResponse;
import dev.haaris.service.NonBlockingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
public class NonBlockingController {
    private final NonBlockingService nonBlockingService;
    @Autowired
    public NonBlockingController(final NonBlockingService nonBlockingService){
        this.nonBlockingService = nonBlockingService;
    }
    @GetMapping("/non-blocking/resource")
    public Mono<CustomResponse> hello() {
        return nonBlockingService.get();
    }
    @GetMapping("/non-blocking/resource/mono")
    public Mono<CustomResponse> hello2() {
        return Mono.fromSupplier(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                //eh, sad but don't care for tutorial
            }
            return new CustomResponse("Done");
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
