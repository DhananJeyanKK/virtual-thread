package org.thread.sec04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thread.sec03.CooperativeSchedule;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class RaceCondition {

    private static final Logger log = LoggerFactory.getLogger(RaceCondition.class);

    private static final List<Integer> list = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {

        demo(Thread.ofVirtual());
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


    private static void inMemoryTask(){
        list.add(1);
    }





}
