package com.gd.halo.util;

import android.content.Context;
import android.support.annotation.RawRes;

import java.io.InputStream;

/**
 * Created by zhouxin on 2016/6/2.
 * Description:
 */
public class ResUtil {

    public static InputStream getRowInputStream(Context context, @RawRes int id){
        return context.getResources().openRawResource(id);
    }


}
