package org.thread.sec05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thread.sec04.ReentrantLock;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.ThreadFactory;

public class ThreadFactory1 {

    private static final Logger log = LoggerFactory.getLogger(ThreadFactory1.class);

    public static void main(String[] args) throws InterruptedException {
        demo(Thread.ofVirtual().name("Dhanan",1).factory());

        Thread.sleep(Duration.ofSeconds(3));
    }


    private static void demo(ThreadFactory threadFactory){
        for(int i=0; i<3;i++){
            var t = threadFactory.newThread(()->{
                log.info("Task Started {}", Thread.currentThread());
                var ct = threadFactory.newThread(()->{
                    log.info("Child Task Started {}", Thread.currentThread());
                    try {
                        Thread.sleep(Duration.ofSeconds(2));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("Child Task Ended {} ", Thread.currentThread());
                });
                ct.start();

                log.info("Task Ended {} ", Thread.currentThread());
            });
            t.start();
        }
    }


}
