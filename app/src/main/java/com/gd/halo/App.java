package com.gd.halo;

import android.app.Application;
import android.content.Context;

import com.gd.halo.util.CrashHandler;
import com.litesuits.orm.LiteOrm;
import com.lzy.okhttputils.OkHttpUtils;

/**
 * Created by zhouxin on 2016/6/2.
 * Description:
 */
public class App extends Application{

    private static final String DB_NAME = "jztk.db";
    private static Context mAppContext;
    private boolean isDebugMode = BuildConfig.DEBUG;
    public static LiteOrm sDb;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
        setupExceptionCaught();
        initHttp();
        sDb = LiteOrm.newSingleInstance(this, DB_NAME);
        if (BuildConfig.DEBUG) {
            sDb.setDebugged(true);
        }
    }

    private void initHttp() {
        //必须调用初始化
        OkHttpUtils.init(this);
        //以下都不是必须的，根据需要自行选择
        if(isDebugMode) {
            OkHttpUtils.getInstance().debug("OkHttpUtils");     //是否打开调试
        }
    }

    /** 设置异常捕获 */
    private void setupExceptionCaught() {
        if (isDebugMode) {
            CrashHandler instance = CrashHandler.getInstance();
            instance.init(this);
        }
    }

    public static Context getAppContext() {
        return mAppContext;
    }
}
