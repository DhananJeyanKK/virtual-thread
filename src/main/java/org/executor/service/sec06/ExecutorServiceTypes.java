package org.executor.service.sec06;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTypes {

    private static final Logger log = LoggerFactory.getLogger(ExecutorServiceTypes.class);

    public static void main(String[] args) {
       // execute(Executors.newSingleThreadExecutor(),3);
       // execute(Executors.newFixedThreadPool(5),100);
       // execute(Executors.newCachedThreadPool(), 100);
        //execute(Executors.newVirtualThreadPerTaskExecutor(), 200);
        schedule();
    }

    private static void schedule(){
        try(var executorService = Executors.newSingleThreadScheduledExecutor()){
            executorService.scheduleAtFixedRate(()->{
                log.info("Executing task");
            }, 0, 1, TimeUnit.SECONDS);
            ioTask(1); //Make sure to add within try block
        }


    }

    private static void execute(ExecutorService executorService, int taskCount){
        try(executorService){
            for (int i =0; i< taskCount; i++){
                int ij= i;
                executorService.submit(()->ioTask(ij));
            }
            ioTask(1);
        }



    }

    private static void ioTask(int i){

        try{
            log.info("Task Started {}, Thread info {}", i, Thread.currentThread());
            Thread.sleep(Duration.ofSeconds(5));
            log.info("Task executed");
            log.info("Task Ended {}, Thread Info {}", i, Thread.currentThread());
        }catch (Exception e){

        }

    }

}
