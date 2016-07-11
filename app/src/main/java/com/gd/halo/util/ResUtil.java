package com.gd.halo.util;

import android.content.Context;
import android.os.Build;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.RawRes;
import android.support.annotation.StringRes;

import com.gd.halo.App;

import java.io.InputStream;

/**
 * Created by zhouxin on 2016/6/2.
 * Description:
 */
public class ResUtil {

    public static InputStream getRowInputStream(Context context, @RawRes int id){
        return context.getResources().openRawResource(id);
    }

    public static String getString(@StringRes int resId)
    {
        return App.getAppContext().getResources().getString(resId);
    }

    public static String getString(@StringRes int resId, Object... formatArgs)
    {
        return App.getAppContext().getResources().getString(resId, formatArgs);
    }

    public static String[] getStringArray(@ArrayRes int id)
    {
        return App.getAppContext().getResources().getStringArray(id);
    }

    public static int[] getIntArray(@ArrayRes int id)
    {
        return App.getAppContext().getResources().getIntArray(id);
    }

    public static int getColor(@ColorRes int id)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return App.getAppContext().getResources().getColor(id, App.getAppContext().getTheme());
        } else {
            return App.getAppContext().getResources().getColor(id);
        }
    }
}
