package org.completable.future.sec09;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class ThenCombine {

    private static final Logger log = LoggerFactory.getLogger(ThenCombine.class);

    void main(){

        try(var ex = Executors.newVirtualThreadPerTaskExecutor()){
            var cf1 = getDeltaAirlines(ex);
            var cf2 = getFrontier(ex);

            var bestDeal = cf1.thenCombine(cf2, ((airFare, airFare2) -> airFare.b() <= airFare2.b() ? airFare : airFare2))
                            .thenApply(airFare -> new AirFare(airFare.a(), airFare.b()-100))
                                    .join();


            log.info("Airline {} ", bestDeal);
        }

    }

    private static CompletableFuture<AirFare> getDeltaAirlines(ExecutorService executorService){
        return CompletableFuture.supplyAsync(()->{
            var random = ThreadLocalRandom.current().nextInt(100, 1000);
            try {
                Thread.sleep(Duration.ofMillis(random));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("Delta - {}", random);

            return new AirFare("Delta", random);
        }, executorService);

    }

    private static CompletableFuture<AirFare> getFrontier(ExecutorService executorService){
        return CompletableFuture.supplyAsync(()->{
            var random = ThreadLocalRandom.current().nextInt(100, 1000);
            try {
                Thread.sleep(Duration.ofMillis(random));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("Frontier - {}", random);
            return new AirFare("frontier", random);
        }, executorService);

    }

}
