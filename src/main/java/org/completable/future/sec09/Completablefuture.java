package org.completable.future.sec09;

import org.executor.service.sec06.ExecutorServiceTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class Completablefuture {

    private static final Logger log = LoggerFactory.getLogger(Completablefuture.class);


    void main() throws InterruptedException {
        log.info("main starts");

        var cf = slowTask();
        cf.thenAccept(v->log.info("value {}", v)); // here main thread doesn't block
      //  cf.get() //complete time exception
       // log.info("value {} ", cf.join()); // don't proceed further until completable future completes the task. main thread is blocked.

        Thread.sleep(Duration.ofSeconds(2));

        log.info("main end");
    }

    private static CompletableFuture<String> fastTask(){
        log.info("method start");
        var cf = new CompletableFuture<String>();
        cf.complete("hi");
        log.info("method end");
        return cf;
    }

    private static CompletableFuture<String> slowTask(){
        log.info("method start");
        var cf = new CompletableFuture<String>();
        Thread.ofVirtual().start(()->{
            try {
                Thread.sleep(Duration.ofSeconds(1));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            cf.complete("hi");
        });
        log.info("method end");
        return cf;
    }

}
