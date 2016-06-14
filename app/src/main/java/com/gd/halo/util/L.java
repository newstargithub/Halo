package com.gd.halo.util;

import android.util.Log;

/**
 * Created by zhouxin on 2016/6/2.
 * Description:
 */
public class L {
    private static final String DEFAULT_TAG = "halo";

    public static void d(String msg){
        d(DEFAULT_TAG, msg);
    }

    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void e(String msg){
        e(DEFAULT_TAG, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }
}
