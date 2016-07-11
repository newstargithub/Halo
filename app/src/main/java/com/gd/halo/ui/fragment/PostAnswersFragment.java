package com.gd.halo.ui.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.gd.halo.bean.PostAnswers;
import com.gd.halo.net.ZhihuJsonCallback;
import com.gd.halo.ui.adapter.PostAnswersAdapter;
import com.gd.halo.util.API;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhouxin on 2016/7/11.
 * Description:
 */
public class PostAnswersFragment extends LoadListFragment{
    List<PostAnswers.AnswersBean> mAnswers = new ArrayList<>();

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new PostAnswersAdapter(mAnswers, mListener);
    }

    @Override
    protected boolean canRefresh() {
        return true;
    }

    @Override
    protected boolean canLoad() {
        return true;
    }

    @Override
    protected void initData() {
        swipe_refresh_layout.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        /*
        用途：获取单篇文章的答案列表
        参数：日期（必选，8位数字，如20140925）、名称（必选：yesterday、recent、archive）
        */
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String format = sdf.format(calendar.getTime());
        String url = API.GET_POST_ANSWERS + "/" + format + "/yesterday";
        String cacheKey = API.GET_POST_ANSWERS;
        OkHttpUtils.get(url)
            .tag(this)
        .cacheKey(cacheKey)
        .cacheMode(CacheMode.DEFAULT)
        .execute(new ZhihuJsonCallback<PostAnswers>(PostAnswers.class) {
            @Override
            public void onResponse(boolean isFromCache, PostAnswers postAnswers, Request request, @Nullable Response response) {
                mAnswers.clear();
                mAnswers.addAll(postAnswers.getAnswers());
                onRefreshFinish();
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                if(e != null) {
                    showToast(e.getMessage());
                }
                onRefreshError();
            }
        });
    }

    public static Fragment newInstance() {
        return new PostAnswersFragment();
    }
}
