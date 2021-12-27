package dev.haaris.service;

import dev.haaris.model.CustomResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class BlockingService {
    private static Logger logger = LogManager.getLogger(BlockingService.class);
    public CustomResponse get() throws InterruptedException {
        logger.info("Sleeping for 5 seconds");
        Thread.sleep(5000);
        logger.info("Still not done, sleeping for 5 seconds");
        Thread.sleep(5000);
        logger.info("Done");
        return new CustomResponse("Done");
    }

}
