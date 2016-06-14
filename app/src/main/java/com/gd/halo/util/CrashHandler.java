package com.gd.halo.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 崩溃日志记录和上传
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler{
    private static final String TAG = CrashHandler.class.getSimpleName();
    private static final String PATH = Environment.getExternalStorageDirectory().getPath()
            + "/Crash/log";
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFFIX = ".trace";
    private static CrashHandler instance;
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private Context mContext;
    private boolean Debug = true;

    private CrashHandler() {

    }

    public static CrashHandler getInstance() {
        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }

    public void init(Context context){
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            dumpExceptionToSDCard(ex);
            uploadExceptionToServer(ex);
        } catch (Exception e){
            e.printStackTrace();
        }

        if(mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(thread, ex);
        } else {
            Process.killProcess(Process.myPid());
        }
    }



    /**
     *  导出异常信息到SD卡
     * @param ex
     */
    private void dumpExceptionToSDCard(Throwable ex) {
        //如果SD卡不存在或无法使用则无法保存信息到SD卡
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if(Debug) {
                Log.d(TAG, "sdcard unmounted, skip dump exception");
            }
            return;
        }
        File dir = new File(PATH);
        if(!dir.exists()) {
            dir.mkdirs();
        }
        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(current));
        File file = new File(dir, FILE_NAME + time + FILE_NAME_SUFFIX);
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.print(time);
            dumpPhoneInfo(pw);
            pw.println();
            ex.printStackTrace(pw);
            pw.close();
        } catch (Exception e) {
            Log.e(TAG, "dump crash info failed");
            e.printStackTrace();
        }
    }

    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo info = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.print("App Version: ");
        pw.print(info.versionName);
        pw.print("_");
        pw.print(info.versionCode);

        //Android版本号
        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.print(Build.VERSION.SDK_INT);

        //手机制造商
        pw.print("Vendor: ");
        pw.print(Build.MANUFACTURER);

        //手机型号
        pw.print("Model: ");
        pw.print(Build.MODEL);

        //CUP架构
        pw.print("CPU ABI: ");
        pw.print(Build.CPU_ABI);
    }

    /**
     *  上传异常信息到服务器
     * @param ex
     */
    private void uploadExceptionToServer(Throwable ex) {
    }
}
