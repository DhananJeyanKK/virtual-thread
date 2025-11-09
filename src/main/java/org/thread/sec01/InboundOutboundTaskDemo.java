package org.thread.sec01;

import java.util.concurrent.CountDownLatch;

public class InboundOutboundTaskDemo {

    public static void main(String[] args) throws InterruptedException {
        platformThreadDemo2();
    }

    private static void platformThreadDemo(){
        for (int i=0; i<10;i++){
            int finalI = i;
            Thread thread = new Thread(() -> Task.ioIntensive(finalI));
            thread.start();
        }
    }

    /**
     *     User Thread, Non-demon thread.
     *     Java 21 offers a builder to create the threads
     *     Use unstarted to start the task
     *     Application waits until everything is completed
     */
    private static void platformThreadDemo1(){
        var builder = Thread.ofPlatform().name("Dhanan",1);
        for (int i=0; i<10;i++){
            int finalI = i;
            Thread thread = builder.unstarted(()->Task.ioIntensive(finalI));
            thread.start();
        }
    }

    /**
     *      Demon threads also do some task but here, the task will be completed immediate.
     *      Application doesn't wait all this threads to complete.
     *      This is background threads
     *      CountDownLatch is something to make application wait.
     */
    private static void platformThreadDemo2() throws InterruptedException {
        var latch = new CountDownLatch(20);
        var builder = Thread.ofPlatform().daemon().name("Dhanan",1);
        for (int i=0; i<20;i++){
            int finalI = i;
            Thread thread = builder.unstarted(()-> {
                Task.ioIntensive(finalI);
                latch.countDown();});
            thread.start();
        }
        latch.await();
    }

    /**
     * Virtual Threads are daemon thread by default.
     * We can't create non-daemon thread.
     */
    private static void virtualThreadDemo() throws InterruptedException {
        var builder = Thread.ofVirtual().name("Dhanan-");
        var latch = new CountDownLatch(20);
        for (int i=0; i< 20; i++){
            int finalI = i;
            Thread thread = builder.unstarted(()-> {
                Task.ioIntensive(finalI);
                latch.countDown();
            });
            thread.start();
        }
        latch.await();
    }
}
