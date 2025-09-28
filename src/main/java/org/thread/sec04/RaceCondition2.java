package org.thread.sec04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class RaceCondition2 {
    private static final Logger log = LoggerFactory.getLogger(RaceCondition2.class);

    private static final List<Integer> list = new ArrayList<>(); //Not thread safe

    static{
        System.setProperty("jdk.tracePinnedThreads","short");
    }


    /**
     * We make method Synchronized - In order to avoid thread block.
     *
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        Runnable runnable = () -> log.info("......Text msg....");

        demo(Thread.ofVirtual());
       //demo(Thread.ofPlatform());
       Thread.ofVirtual().start(runnable);
        Thread.sleep(Duration.ofSeconds(15));
    }


    private static void demo(Thread.Builder builder){
        for(int i=0; i<50;i++){
            builder.start(()->{
                log.info("Task Started {}", Thread.currentThread());

                    try {
                        ioTask();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                log.info("Thread Ended {} ", Thread.currentThread());
            });
        }
    }


    /**
     *
     *
     *
     * @throws InterruptedException
     */
    private static synchronized void ioTask() throws InterruptedException {
        list.add(1);
        Thread.sleep(Duration.ofSeconds(10));
    }

}
