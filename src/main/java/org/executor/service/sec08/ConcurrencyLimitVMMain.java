package org.executor.service.sec08;

import org.executor.service.sec07.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrencyLimitVMMain {

    static final Logger log = LoggerFactory.getLogger(ConcurrencyLimitVMMain.class);

    static void main() throws Exception {
        var factory = Thread.ofVirtual().name("dhana", 1).factory();
        var limiter = new VirtualThreadConcurrencyLimiter(Executors.newThreadPerTaskExecutor(factory), 1);
        execute(limiter, 20);

    }

    private static void execute(VirtualThreadConcurrencyLimiter virtualThreadConcurrencyLimiter, int taskCount) throws Exception {
        try(virtualThreadConcurrencyLimiter){
            for (int i =1; i<= taskCount; i++){
                int ij= i;
                virtualThreadConcurrencyLimiter.submit(()-> printProductInfo(ij));
            }
        }
    }

    //3rd Party
    //Contract : 3 concurrent calls allowed
    private static String printProductInfo(int id){
        var product = Client.getProduct(id);

        log.info("{} -> {}", id, product);
        return product;
    }
}
