package org.thread.sec04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class RaceCondition1 {

    private static final Logger log = LoggerFactory.getLogger(RaceCondition.class);

    private static final List<Integer> list = new ArrayList<>(); //Not thread safe

    /**
     * We make method Synchronized - In order to avoid thread block.
     *
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        demo(Thread.ofVirtual());
        demo(Thread.ofPlatform());
        Thread.sleep(Duration.ofSeconds(2));
        log.info("total list size {} ", list.size());
    }


    private static void demo(Thread.Builder builder){
        for(int i=0; i<50;i++){
            builder.start(()->{
                log.info("Task Started {}", Thread.currentThread());
                for(int j=0; j<200;j++){
                    inMemoryTask();
                }
                log.info("Thread Ended {} ", Thread.currentThread());
            });
        }
    }


    private static synchronized void inMemoryTask(){
        list.add(1);
    }

}
