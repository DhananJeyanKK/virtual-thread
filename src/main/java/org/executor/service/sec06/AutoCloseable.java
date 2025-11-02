package org.executor.service.sec06;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.Duration;
import java.util.concurrent.Executors;

public class AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(AutoCloseable.class);

     void main() {

        var exeService = Executors.newSingleThreadExecutor();
        exeService.submit(AutoCloseable::task);
        exeService.submit(AutoCloseable::task);
        exeService.submit(AutoCloseable::task);
        exeService.submit(AutoCloseable::task);
        exeService.submit(AutoCloseable::task);
        log.info("Submitted"); //Automatic update
    }

    private static void task() {
        try {
            Thread.sleep(Duration.ofSeconds(1));
            log.info("Task executed");
        } catch (Exception e) {

        }

    }


}
