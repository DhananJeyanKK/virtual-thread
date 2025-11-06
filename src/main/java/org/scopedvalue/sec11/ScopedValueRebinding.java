package org.scopedvalue.sec11;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class ScopedValueRebinding {

    private static final Logger log = LoggerFactory.getLogger(ScopedValueRebinding.class);

    private static final ScopedValue<String> sessionTokenHandler = ScopedValue.newInstance();

    static void main() throws InterruptedException {
        //sessionTokenHandler.set(authenticate());
        // orderController();
        webFilter(()-> orderController()); // now it has different id
     //   webFilter(()->orderController()); // now it has different id
        //sessionTokenHandler.remove(); // When there is exception in Order controller then this wont be executed.
        //  orderController(); // same thread is used without calling remove method.
        Thread.sleep(10);
    }

    private static void webFilter(Runnable runnable){
       ScopedValue.where(sessionTokenHandler, authenticate()).run(runnable);
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
       // Thread.ofVirtual().name("Product-1").start(ScopedValueRebinding::callProductService);
        ScopedValue.where(sessionTokenHandler, "test").run(ScopedValueRebinding::callProductService);
        callInventoryService();
    }

    private static void callProductService(){
        log.info("Product Service {} ", sessionTokenHandler.get());
       // sessionTokenHandler.set("testing"); // it changes only to Produc-1 thread.

        log.info("Product After change Service {} ", sessionTokenHandler.get());
    }

    private static void callInventoryService(){
        log.info("Inventory Service {}", sessionTokenHandler.get());
    }


}
