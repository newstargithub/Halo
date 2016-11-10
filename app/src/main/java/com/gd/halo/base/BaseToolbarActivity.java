package com.gd.halo.base;

import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.gd.halo.R;

/**
 * Created by zhouxin on 2016/11/10.
 * Description:
 */
public abstract class BaseToolbarActivity extends BaseActivity{
    private AppBarLayout mAppBar;
    private Toolbar mToolbar;
    private boolean mIsHidden;

    @Override
    protected void initViewsAndEvents() {
        mAppBar = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar == null || mAppBar == null) {
            throw new IllegalStateException(
                    "The subclass of ToolbarActivity must contain a toolbar.");
        }
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onToolbarClick();
            }
        });
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            /** 注意 如果你想要通过toolbar.setTitle(“主标题”);设置Toolbar的标题，
             * 你必须在调用它之前调用如下代码：用来隐藏系统默认的Title。*/
            actionBar.setDisplayShowTitleEnabled(false);
            //向上箭头
            actionBar.setDisplayHomeAsUpEnabled(canBack());
        }
        /*mToolbar.setTitle("主标题");
        mToolbar.setTitleTextColor(Color.RED);
        mToolbar.setSubtitle("子标题");*/
        if (Build.VERSION.SDK_INT >= 21) {
            mAppBar.setElevation(10.6f);
        }
    }

    public void onToolbarClick() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 返回箭头
     *
     * @return
     */
    public boolean canBack() {
        return false;
    }

    protected void setActionBarTitle(int resId) {
        setActionBarTitle(getString(resId));
    }

    protected void setActionBarTitle(String title) {
        if(mToolbar != null) {
            mToolbar.setTitle(title);
        }
    }

    protected void setAppBarAlpha(float alpha) {
        mAppBar.setAlpha(alpha);
    }

    protected void hideOrShowToolbar() {
        mAppBar.animate()
                .translationY(mIsHidden ? 0 : -mAppBar.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        mIsHidden = !mIsHidden;
    }

}
