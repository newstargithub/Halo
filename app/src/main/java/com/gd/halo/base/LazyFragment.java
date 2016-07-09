package com.gd.halo.base;

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
 * Created by zhouxin on 2016/6/3.
 * Description:
 */
public abstract class LazyFragment extends Fragment{
    /**
     * Log tag
     */
    protected String TAG = null;

    protected Context mContext;
    private View rootView;
    private boolean isViewCreated;
    private boolean isFirstResume = true;
    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;
    private Toast mToast;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        L.d(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        L.d(TAG, "onCreateView");
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
        L.d(TAG, "onViewCreated");
        isViewCreated = true;
        if(getUserVisibleHint()) {
            onVisibleToUser();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(!isLazyLoad()) {
            init();
        }
        L.d(TAG, "onActivityCreated");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        L.d(TAG, "onDestroyView");
        isViewCreated = false;
        isFirstResume = true;
        isFirstVisible = true;
        isFirstInvisible = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.d(TAG, "onDestroy");
    }

    protected void onFirstUserVisible() {
        if(isLazyLoad()) {
            init();
        }
    }

    private void init() {
        initViewsAndEvents();   //初始化View和事件
        initData();     //初始化数据
    }

    protected boolean isLazyLoad(){
        return true;
    }

    protected abstract void initViewsAndEvents();

    protected abstract void initData();

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

    protected void onUserVisible() {

    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            onUserInvisible();
        }
    }

    protected void onUserInvisible() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if(isViewCreated) {
                onVisibleToUser();
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

    private void onVisibleToUser() {
        if (isFirstVisible) {
            isFirstVisible = false;
            onFirstUserVisible();
        } else {
            onUserVisible();
        }
    }

    protected void onFirstUserInvisible() {

    }

    protected <T> T findViewById(int id) {
        return (T) rootView.findViewById(id);
    }

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


}
