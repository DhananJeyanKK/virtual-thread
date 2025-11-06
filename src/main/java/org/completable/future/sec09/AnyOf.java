package org.completable.future.sec09;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class AnyOf {

    private static final Logger log = LoggerFactory.getLogger(AnyOf.class);

    void main(){

        try(var ex = Executors.newVirtualThreadPerTaskExecutor()){
            var cf1 = getDeltaAirlines(ex);
            var cf2 = getFrontier(ex);

            log.info("Airline {} ", CompletableFuture.anyOf(cf1, cf2).join());
        }

    }

    private static CompletableFuture<String> getDeltaAirlines(ExecutorService executorService){
        return CompletableFuture.supplyAsync(()->{
            var random = ThreadLocalRandom.current().nextInt(100, 1000);
            try {
                Thread.sleep(Duration.ofMillis(random));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Delta" + random;
        }, executorService);

    }

    private static CompletableFuture<String> getFrontier(ExecutorService executorService){
        return CompletableFuture.supplyAsync(()->{
            var random = ThreadLocalRandom.current().nextInt(100, 1000);
            try {
                Thread.sleep(Duration.ofMillis(random));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Frontier" + random;
        }, executorService);

    }
}
