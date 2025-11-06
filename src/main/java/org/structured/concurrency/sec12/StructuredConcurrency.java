package org.structured.concurrency.sec12;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.StructuredTaskScope;

public class StructuredConcurrency {

    public static final Logger log = LoggerFactory.getLogger(StructuredConcurrency.class);

    static void main() {
        try(var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.anySuccessfulResultOrThrow())){
            //submit the subtasks
            var subTask1 = scope.fork(FlightPriceService::getDeltaAirlines);
            var subTask2 = scope.fork(FlightPriceService::getFrontierAirlines);
           // var subTask2 = scope.fork(FlightPriceService::getFrontierAirlines);

            //scope.join(); //awaitAll wait for all the subtasks to complete
            //allSuccessfulOrThrow - All subtask should be completed successfully. If one of the subtask fails then cancells the tasks
            //anySuccessfulResultOrThrow - Whoever completes first it goes

            log.info("Result is available {}", scope.join());


            log.info("subtasks1 state: {}", subTask1.state());
            log.info("subtasks2 state: {}", subTask2.state());

            //get result
           // log.info("subtask1 result : {}", subTask1.get());
            //log.info("subtask2 result : {}", subTask2.get());
           // log.info("subtask2 result : {}", subTask2.exception().getMessage()); //this will give you message, you should not use get..
            //you should check state and then proceed.



        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


}
