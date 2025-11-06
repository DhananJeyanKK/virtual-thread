package org.scopedvalue.sec11;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScoppedValue {

    public static final Logger log = LoggerFactory.getLogger(ScoppedValue.class);

    public static final ScopedValue<String> SESSION_KEY = ScopedValue.newInstance();

    static void main() {
        ScopedValue.where(SESSION_KEY, "session-1").run(() -> checkBinding()); //value is set and removed
        checkBinding();
    }

    public static void checkBinding(){
        log.info("is Bound {}", SESSION_KEY.isBound());
       // log.info("value {}", SESSION_KEY.get()); //Dont invoke if value is not bound.
        log.info("value with dummy {}", SESSION_KEY.orElse("dummy"));
    }
}
