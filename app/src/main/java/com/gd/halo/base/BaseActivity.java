package com.gd.halo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gd.halo.R;
import com.gd.halo.support.sp.AppConfigure;

/**
 * Created by zhouxin on 2016/6/15.
 * Description:
 */
public class BaseActivity extends AppCompatActivity{
    protected Context mContext;
    private boolean isDayTheme;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        isDayTheme = AppConfigure.isDayTheme(mContext);
        setTheme(isDayTheme ? R.style.DayTheme : R.style.NightTheme);
    }

    /**
     * 切换主题
     */
    public void switchTheme(){
        isDayTheme = !isDayTheme;
        AppConfigure.setDayTheme(mContext, isDayTheme);
        recreate(); //重新创建个实例，如配置改变。
    }

}
