package org.structured.concurrency.sec12;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thread.utils.CommonUtils;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

public class FlightPriceService {

    public static final Logger log = LoggerFactory.getLogger(FlightPriceService.class);

    public static String getDeltaAirlines(){
        log.info("Calling delta");

        CommonUtils.sleep("delta", Duration.ofSeconds(1));
        return "Delta" + ThreadLocalRandom.current().nextInt(100, 1000);
    }

    public static String getFrontierAirlines(){
        log.info("Calling Frontier");

        CommonUtils.sleep("frontier", Duration.ofSeconds(2));
        return "Frontier" + ThreadLocalRandom.current().nextInt(100, 1000);
    }

    public static String getUnitedAirfare(){
        log.info("calling united");
        throw new RuntimeException("Service unavailable");
    }


}
