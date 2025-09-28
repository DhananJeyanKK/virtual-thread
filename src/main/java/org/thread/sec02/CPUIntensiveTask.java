package org.thread.sec02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thread.utils.CommonUtils;

import java.util.concurrent.CountDownLatch;

public class CPUIntensiveTask {

    private static final Logger log = LoggerFactory.getLogger(CPUIntensiveTask.class);

    /**
     * My machine is 12 cores -> Meaning It is executing the thread on each of its processors.
     *
     *
     */
    private static final int TASK_COUNT= 2 * Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        log.info("Tasks Count: {}", TASK_COUNT);
        for (int i = 0; i < 3; i++) {
            var totalTimeTaken = CommonUtils.timer(() -> demo(Thread.ofVirtual()));
            log.info("Total time taken with virtual {} ms", totalTimeTaken);
            totalTimeTaken = CommonUtils.timer(() -> demo(Thread.ofPlatform()));
            log.info("Total time taken with platform {} ms", totalTimeTaken);
        }
    }

    private static void demo(Thread.Builder builder){
        var latch = new CountDownLatch(TASK_COUNT);
        for(int i=1; i<= TASK_COUNT; i++){
            builder.start(()->{
               Task.cpuIntensive(45);
               latch.countDown();
            });
        }
        try{
            latch.await();
        }catch (Exception e){
            throw new RuntimeException();
        }
    }
}
