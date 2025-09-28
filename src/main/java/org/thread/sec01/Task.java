package org.thread.sec01;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.plaf.TableHeaderUI;
import java.time.Duration;


public class Task {

    static final Logger log = LoggerFactory.getLogger(Task.class);

    public static void ioIntensive(int i) {
        try{
            log.info("Starting IO task {}. Thread info {} ",i, Thread.currentThread());
            Thread.sleep(Duration.ofSeconds(10));
            log.info("Ending IO task {}. Thread info {} ",i, Thread.currentThread());
        }catch(InterruptedException interruptedException){
            throw new RuntimeException();
        }
    }
}
