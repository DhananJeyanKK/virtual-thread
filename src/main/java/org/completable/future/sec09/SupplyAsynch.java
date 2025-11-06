package org.completable.future.sec09;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class SupplyAsynch {

    private static final Logger log = LoggerFactory.getLogger(SupplyAsynch.class);

    void main() throws InterruptedException {
        log.info("main starts");

        var cf = slowTask();
        cf.thenAccept(v->log.info("value {}", v));

        Thread.sleep(Duration.ofSeconds(2));

        log.info("main end");
    }

    private static CompletableFuture<String> slowTask(){
        log.info("method start");
        var cf = CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(Duration.ofSeconds(1));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "hi";
        }, Executors.newVirtualThreadPerTaskExecutor());

        log.info("method end");
        return cf;
    }


}
