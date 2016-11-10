package com.gd.halo.base;

import android.os.Bundle;

import com.gudeng.library.base.BasePresenter;

/**
 * Created by zhouxin on 2016/11/10.
 * Description:
 */
public abstract class GMVPBaseFragment<V, T extends BasePresenter<V>> extends GBaseFragment{
    protected T mPresenter;

    protected abstract T createPresenter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

}
