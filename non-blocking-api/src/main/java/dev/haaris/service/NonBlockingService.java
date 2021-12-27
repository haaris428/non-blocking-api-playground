package dev.haaris.service;

import dev.haaris.model.CustomResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class NonBlockingService {
    private static final Logger logger = LogManager.getLogger(NonBlockingService.class);
    private static final ExecutorService executorService = Executors.newFixedThreadPool(100);
    public Mono<CustomResponse> get(){
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            try {
                logger.info("Starting get resource");
                logger.info("Sleeping for 5 seconds");
                Thread.sleep(5000);
                logger.info("Still not done, sleeping for 5 seconds");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                logger.info("Something bad happened");
            }
            logger.info("Done");
            return new CustomResponse("Done");
        }, executorService));
    }
}
