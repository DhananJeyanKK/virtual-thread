package org.thread.sec02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thread.utils.CommonUtils;

public class Task {

    private static final Logger log = LoggerFactory.getLogger(Task.class);

    public static void cpuIntensive(int i){
        var timeTaken = CommonUtils.timer(() -> findFib(i));
    }

    // 2 ^ N algorithm - intentionally done this way to simulate CPU intensive task
    public static long findFib(long input){
        if(input < 2)
            return input;
        return findFib(input - 1) + findFib(input - 2);
    }


}
