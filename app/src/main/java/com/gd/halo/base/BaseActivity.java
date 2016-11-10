package com.gd.halo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.gd.halo.R;
import com.gd.halo.support.sp.AppConfigure;
import com.gd.halo.view.GBaseView;
import com.gudeng.library.netstatus.NetChangeObserver;
import com.gudeng.library.netstatus.NetStateReceiver;
import com.gudeng.library.netstatus.NetUtils;
import com.gudeng.library.widget.VaryViewHelperController;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zhouxin on 2016/6/15.
 * Description:
 */
public abstract class BaseActivity extends AppCompatActivity implements GBaseView {
    protected Context mContext;
    private boolean isDayTheme;
    private NetChangeObserver mNetChangeObserver;
    private VaryViewHelperController mVaryViewHelperController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        isDayTheme = AppConfigure.isDayTheme(mContext);
        setTheme(isDayTheme ? R.style.DayTheme : R.style.NightTheme);
        initArguments();
        if(isRegisterEvent()) {
            registerEvent();
        }
        mNetChangeObserver = new NetChangeObserver() {
            @Override
            public void onNetConnected(NetUtils.NetType type) {
                super.onNetConnected(type);
                onNetworkConnected(type);
            }

            @Override
            public void onNetDisConnect() {
                super.onNetDisConnect();
                onNetworkDisConnected();
            }
        };
        NetStateReceiver.registerObserver(mNetChangeObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isRegisterEvent()) {
            unregisterEvent();
        }
        NetStateReceiver.removeRegisterObserver(mNetChangeObserver);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        if (null != getLoadingTargetView()) {
            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
        }
        initViewsAndEvents();
    }

    /**
     * 切换主题
     */
    public void switchTheme(){
        isDayTheme = !isDayTheme;
        AppConfigure.setDayTheme(mContext, isDayTheme);
        recreate(); //重新创建个实例，如配置改变。
    }

    /**
     * 初始化传递的参数
     */
    protected void initArguments() {

    }

    /**
     * 初始化控件
     */
    protected abstract void initViewsAndEvents();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * get loading target view
     */
    protected abstract View getLoadingTargetView();

    /**
     * @return  是否注册事件
     */
    protected abstract boolean isRegisterEvent();

    /**
     * network connected
     */
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    /**
     * network disconnected
     */
    protected void onNetworkDisConnected() {

    }

    /**
     * toggle show loading
     *
     * @param toggle
     */
    protected void toggleShowLoading(boolean toggle, String msg) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showLoading(msg);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show empty
     *
     * @param toggle
     */
    protected void toggleShowEmpty(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showEmpty(msg, onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show error
     *
     * @param toggle
     */
    protected void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showError(msg, onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show network error
     *
     * @param toggle
     */
    protected void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showNetworkError(onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    @Override
    public void showError(String msg) {
        toggleShowError(true, msg, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }

    @Override
    public void showException(String msg) {
        toggleShowError(true, msg, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }

    @Override
    public void showNetError() {
        toggleNetworkError(true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }

    @Override
    public void showLoading(String msg) {
        toggleShowLoading(true, null);
    }

    @Override
    public void hideLoading() {
        toggleShowLoading(false, null);
    }

    /**
     * 注册事件
     */
    private void registerEvent() {
        EventBus.getDefault().register(this);
    }

    /**
     * 解注册事件
     */
    private void unregisterEvent() {
        EventBus.getDefault().unregister(this);
    }

    /**
     * 显示土司
     * @param msg
     */
    protected void showToast(String msg){
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(@StringRes int resId){
        showToast(getString(resId));
    }


}
