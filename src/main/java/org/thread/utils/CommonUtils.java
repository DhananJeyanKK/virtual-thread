package org.thread.utils;

public class CommonUtils {

    public static long timer(Runnable runnable){
        var start = System.currentTimeMillis();
        runnable.run();
        var end = System.currentTimeMillis();
        return end-start;
    }

}
