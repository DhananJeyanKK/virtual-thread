package org.thread.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class CommonUtils {

    public static final Logger log = LoggerFactory.getLogger(CommonUtils.class);


    public static void sleep(String taskName, Duration duration){
        try{
            Thread.sleep(duration);
        }catch (InterruptedException interruptedException){
            log.info("task is cancelled {}", taskName);
        }
    }

    public static long timer(Runnable runnable){
        var start = System.currentTimeMillis();
        runnable.run();
        var end = System.currentTimeMillis();
        return end-start;
    }

}
