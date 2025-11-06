package org.structured.concurrency.sec12;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.StructuredTaskScope;

public class ScopedValueInhertable {

    private static final Logger log = LoggerFactory.getLogger(ScopedValueInhertable.class);

    private static final ScopedValue<String> sessionTokenHandler = ScopedValue.newInstance();

    static void main() throws InterruptedException {

        webFilter(()-> orderController());
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

        try(var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.allSuccessfulOrThrow())){
            scope.fork(ScopedValueInhertable::callInventoryService);
            scope.fork(ScopedValueInhertable::callProductService);
            scope.join();
        }catch (InterruptedException interruptedException){
            throw new RuntimeException(interruptedException);
        }



    }

    private static void callProductService(){
        log.info("Product Service {} ", sessionTokenHandler.get());
    }

    private static void callInventoryService(){
        log.info("Inventory Service {}", sessionTokenHandler.get());
    }


}
