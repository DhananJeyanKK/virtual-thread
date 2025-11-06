package org.threadlocal.sec10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class InheritableThread {

    private static final Logger log = LoggerFactory.getLogger(InheritableThread.class);

    private static final ThreadLocal<String> sessionTokenHandler = new InheritableThreadLocal<>();

    static void main(){
        //sessionTokenHandler.set(authenticate());
        // orderController();
        webFilter(()-> orderController()); // now it has different id
     //   webFilter(()->orderController()); // now it has different id
        //sessionTokenHandler.remove(); // When there is exception in Order controller then this wont be executed.
        //  orderController(); // same thread is used without calling remove method.
    }

    private static void webFilter(Runnable runnable){

        try{
            sessionTokenHandler.set(authenticate());
            runnable.run();
        }finally {
            sessionTokenHandler.remove(); // Does it clear the child thread values?
            // Virtual threads are short lived so not a problem to remove.
            // If you are creating new platform thread then you have to be carefull.
        }
    }

    private static String authenticate(){
        var toke = UUID.randomUUID().toString();
        log.info("token={}", toke);
        return toke;
    }

    private static void orderController(){
        log.info("order {}", sessionTokenHandler.get());
        orderService();
    }

    private static void orderService(){
        log.info("Session in Order Service {} ", sessionTokenHandler.get());
        Thread.ofVirtual().name("Product-1").start(InheritableThread::callProductService); // here thread local is null - user InheritableThreadLocal
        Thread.ofVirtual().name("Inventory-1").start(InheritableThread::callInventoryService); // here thread local is null - use InheritableThreadLocal

       // callProductService(); // two service call, We can use virtual thread here.
        //callInventoryService();
    }

    private static void callProductService(){
        log.info("Product Service {} ", sessionTokenHandler.get());
        sessionTokenHandler.set("testing"); // it changes only to Produc-1 thread.

        log.info("Product After change Service {} ", sessionTokenHandler.get());
    }

    private static void callInventoryService(){
        log.info("Inventory Service {}", sessionTokenHandler.get());
    }


}
