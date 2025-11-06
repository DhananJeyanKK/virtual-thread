package org.threadlocal.sec10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class TheadLocal {

    private static final Logger log = LoggerFactory.getLogger(TheadLocal.class);

    private static final ThreadLocal<String> sessionTokenHandler = new ThreadLocal<>();

    static void main(){
        //sessionTokenHandler.set(authenticate());
       // orderController();
        webFilter(()-> orderController()); // now it has different id
        webFilter(()->orderController()); // now it has different id
        //sessionTokenHandler.remove(); // When there is exception in Order controller then this wont be executed.
      //  orderController(); // same thread is used without calling remove method.
    }

    private static void webFilter(Runnable runnable){

        try{
            sessionTokenHandler.set(authenticate());
            runnable.run();
        }finally {
            sessionTokenHandler.remove();
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
    }





}
