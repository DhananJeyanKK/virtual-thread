package org.executor.service.sec08;

import org.executor.service.sec07.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thread.sec01.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrencyLimit {

    static final Logger log = LoggerFactory.getLogger(ConcurrencyLimit.class);

    static void main() {
       // var factory = Thread.ofVirtual().factory();
        execute(Executors.newFixedThreadPool(3), 20);
    }

    private static void execute(ExecutorService executorService, int taskCount){
        try(executorService){
            for (int i =0; i<= taskCount; i++){
                int ij= i;
                executorService.submit(()-> printProductInfo(ij));
            }
        }
    }

    //3rd Party
    //Contract : 3 concurrent calls allowed
    private static void printProductInfo(int id){
        log.info("{} -> {}", id, Client.getProduct(id));
    }
}
