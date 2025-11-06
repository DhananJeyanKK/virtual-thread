package org.completable.future.sec09;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class RunAsync {

    private static final Logger log = LoggerFactory.getLogger(RunAsync.class);

    void main() throws InterruptedException {
        log.info("main starts");

        runAsync()
                .thenRun(()->log.info("it's done"))
                        .exceptionally(ex->{
                            log.info("exception occured");
                            return null;
                        });

        Thread.sleep(Duration.ofSeconds(2));

        log.info("main end");
    }

    private static CompletableFuture<Void> runAsync(){
        log.info("method start");

        var cf = CompletableFuture.runAsync(()->{
            try {
                Thread.sleep(Duration.ofSeconds(1));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            throw new RuntimeException("oops");

        }, Executors.newVirtualThreadPerTaskExecutor());// can use factor

        return cf;
    }


}
