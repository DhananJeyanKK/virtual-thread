package org.thread.sec03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thread.sec02.CPUIntensiveTask;

import java.time.Duration;

public class CooperativeSchedule {

    private static final Logger log = LoggerFactory.getLogger(CooperativeSchedule.class);

    static{
        System.setProperty("jdk.virtualThreadScheduler.parellelism", "1"); //Selecting only one core
        System.setProperty("jdk.virtualThreadScheduler.maxPoolSize", "1"); //Carrier thread is only one, Worker thread.
    }

    /**
     * Since we have only one carrier thread, It will execute the thread one by one.
     * This is why Cooperative task.
     *
     * If you want to share the time between different threads during its execution, Then You give option to Thread by saying
     * Thread.yield
     *
     *
     *
     *
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        var builder = Thread.ofVirtual();
        var t1 = builder.unstarted(()->demo(1));
        var t2 = builder.unstarted(()->demo(2));

        t1.start();
        t2.start();

        Thread.sleep(Duration.ofSeconds(2));
    }



    private static void demo(int threadNumber){
        log.info("Thread number started {} ", threadNumber);
        for(int i =0; i < 10; i ++){
            log.info("thread {} is printing {}, Thread {}", threadNumber, i, Thread.currentThread());
            if(threadNumber == 1)
                Thread.yield(); // This is giving other threads an option.
        }
        log.info("Thread number ended {} ", threadNumber);
    }



}
