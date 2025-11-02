package org.executor.service.sec07;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class AggregatorDemo {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    void main() throws Exception{
      var executor = Executors.newVirtualThreadPerTaskExecutor();
      //  var executor = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("dhanan", 1).factory());
        var aggregator = new AggregatorService(executor);

        var futures = IntStream.range(1, 50).mapToObj(id -> executor.submit(()->aggregator.getProductDTO(id))).toList();

        var list = futures.stream().map(AggregatorDemo::toProductDTO).toList();

        log.info("list: {}", list);
    }

    private static ProductDTO toProductDTO(Future<ProductDTO> future){
        try{
            return future.get();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
