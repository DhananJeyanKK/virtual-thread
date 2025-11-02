package org.executor.service.sec07;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AccessResponseUsingFuture {

    private static final Logger log = LoggerFactory.getLogger(AccessResponseUsingFuture.class);

    void main() throws Exception {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            var product1 = executor.submit(() -> Client.getProduct(1));

            var product2 = executor.submit(() -> Client.getProduct(2));

            var product3 = executor.submit(() -> Client.getProduct(3));

            log.info("product-1 : {}", product1.get());
            log.info("product-2 : {}", product2.get());
            log.info("product-3 : {}", product3.get());
        }

    }

}
