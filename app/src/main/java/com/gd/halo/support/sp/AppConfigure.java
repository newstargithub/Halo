package com.gd.halo.support.sp;

import android.content.Context;

/**
 * Created by zhouxin on 2016/6/15.
 * Description:
 */
public class AppConfigure {

    private static final String TAG = AppConfigure.class.getSimpleName();
    private static final String KEY_DAY_THEME = "key_day_theme";

    public static boolean isDayTheme(Context context) {
        return SharedWrapper.with(context, TAG).getBoolean(KEY_DAY_THEME, true);
    }

    public static void setDayTheme(Context context, boolean isDayTheme) {
        SharedWrapper.with(context, TAG).setBoolean(KEY_DAY_THEME, isDayTheme);
    }

}
