package dev.haaris.api;

import dev.haaris.model.CustomResponse;
import dev.haaris.service.BlockingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class BlockingController {
    private final BlockingService blockingService;
    @Autowired
    public BlockingController(final BlockingService blockingService){
        this.blockingService = blockingService;
    }
    @GetMapping("/blocking/resource")
    public Mono<ResponseEntity<CustomResponse>> hello() throws InterruptedException {
        return Mono.just(ResponseEntity.ok(blockingService.get()));
    }
}
