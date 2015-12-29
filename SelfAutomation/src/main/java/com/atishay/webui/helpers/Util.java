package com.atishay.webui.helpers;

/**
 * Created by Ati on 28-12-2015.
 */
public class Util {
    public static void pause(double timeInSec){
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() < (start + (timeInSec * 1000))){
            // do nothing
        }
    }
}
