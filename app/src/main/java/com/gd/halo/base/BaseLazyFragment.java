package com.gd.halo.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gd.halo.util.L;
import com.gd.halo.util.ToastUtil;

/**
 * 懒加载的Fragment，可见时再去初始化数据，不预加载减少流量
 * initData在Fragment第一次可见时才调用
 * initViewsAndEvents在onViewCreated创建view时调用
 * ViewPager.setOffscreenPageLimit(page.size());
 */
public abstract class BaseLazyFragment extends Fragment implements View.OnClickListener {
    /**
     * Log tag
     */
    protected String TAG = null;

    /**
     * context
     */
    protected Activity mContext = null;

    protected View rootView = null;

    private boolean isFirstResume = true;
    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;
    private boolean isPrepared;
    private Toast mToast;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(layoutResID(), container, false);
        return rootView;
    }

    protected View getRootView(){
        return rootView;
    }

    /**
     * bind layout resource file
     *
     * @return id of layout resource
     */
    protected abstract int layoutResID();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        L.d(TAG, "onViewCreated initViewsAndEvents");
        initViewsAndEvents();   //初始化View和事件
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        L.d(TAG, "onActivityCreated");
        initPrepare();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstResume) {
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint()) {
            onUserVisible();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            onUserInvisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        L.d(TAG, "setUserVisibleHint:" + isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
            } else {
                onUserInvisible();
            }
        }
    }

    private synchronized void initPrepare() {
        if (isPrepared) {
            onFirstUserVisible();
        } else {
            isPrepared = true;
        }
    }

    protected View findViewById(int id) {
        return rootView.findViewById(id);
    }

    /**
     * when fragment is visible for the first time, here we can do some initialized work or refresh data only once
     */
    protected void onFirstUserVisible() {
        L.d(TAG, "onFirstUserVisible initData");
        initData();
    }

    /**
     * this method like the fragment's lifecycle method onResume()
     */
    protected void onUserVisible() {

    }

    /**
     * when fragment is invisible for the first time
     */
    private void onFirstUserInvisible() {
        // here we do not recommend do something
    }

    /**
     * this method like the fragment's lifecycle method onPause()
     */
    protected void onUserInvisible() {

    }

    /**
     * init all views and add events
     */
    protected abstract void initViewsAndEvents();

    protected abstract void initData();

    /**
     * show toast
     * @param resId
     */
    protected void showToast(@StringRes int resId) {
        Toast toast = ToastUtil.createCustomLayout(mContext, resId);
        mToast = ToastUtil.show(mToast, toast);
    }

    /**
     * show toast
     * @param msg
     */
    protected void showToast(String msg) {
        Toast toast = ToastUtil.createCustomLayout(mContext, msg);
        mToast = ToastUtil.show(mToast, toast);
    }

    @Override
    public void onClick(View v) {

    }
}
