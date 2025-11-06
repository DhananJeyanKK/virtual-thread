package org.completable.future.sec09;


import org.executor.service.sec07.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class AllOf {

    private static final Logger log = LoggerFactory.getLogger(AllOf.class);

    void main() throws Exception{
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        //  var executor = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("dhanan", 1).factory());
        var aggregator = new AggregatorService(executor);

        var futures = IntStream.range(1, 50)
                .mapToObj(id -> CompletableFuture.supplyAsync(()->
                {
                    try {
                        return aggregator.getProductDTO(id);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }, executor)).toList();

        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();

        var list = futures.stream().map(CompletableFuture::join).toList();

        log.info("list: {}", list);
    }



}
