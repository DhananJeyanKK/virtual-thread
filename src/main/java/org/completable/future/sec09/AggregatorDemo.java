package org.completable.future.sec09;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class AggregatorDemo {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    void main() throws Exception{
      var executor = Executors.newVirtualThreadPerTaskExecutor();

        var aggregator = new AggregatorService(executor);

    //    log.info("Product {} ", aggregator.getProductDTO(51));


    }


}
