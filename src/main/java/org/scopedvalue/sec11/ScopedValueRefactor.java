package org.scopedvalue.sec11;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class ScopedValueRefactor {

    private static final Logger log = LoggerFactory.getLogger(ScopedValueRefactor.class);

    private static final ScopedValue<String> SESSION_TOKEN =ScopedValue.newInstance();

    static void main(){
        ScopedValue.where(SESSION_TOKEN, authenticate()).run(()->orderController());
        ScopedValue.where(SESSION_TOKEN, authenticate()).run(()->orderController());
    }

    private static String authenticate(){
        var toke = UUID.randomUUID().toString();
        log.info("token={}", toke);
        return toke;
    }

    private static void orderController(){
        log.info("order {}", SESSION_TOKEN.get());
        orderService();
    }

    private static void orderService(){
        log.info("Session in Order Service {} ", SESSION_TOKEN.get());
    }





}
