package com.gd.halo.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.gd.halo.R;
import com.gd.halo.base.GLazyFragment;
import com.gd.halo.bean.Posts;
import com.gd.halo.support.json.GsonWrapper;
import com.gd.halo.ui.adapter.ZhiHuAdapter;
import com.gd.halo.util.API;
import com.gd.halo.util.L;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.AbsCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A fragment representing a list of Items.
 * <p />
 * Activities containing this fragment MUST implement the {@link OnZhiHuItemClickListener}
 * interface.
 */
public class ZhiHuFragment extends GLazyFragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;
    private String mTitle;
    private String mUrl;

    private OnZhiHuItemClickListener mListener;
    private ZhiHuAdapter mListAdapter;
    private List<Posts.PostsBean> mList = new ArrayList<>();
    @BindView(R.id.list)
    RecyclerView mRecyclerView;

    // TODO: Customize parameter initialization
    public static ZhiHuFragment newInstance() {
        ZhiHuFragment fragment = new ZhiHuFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ZhiHuFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int layoutResID() {
        return R.layout.fragment_zhihu_list;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnZhiHuItemClickListener) {
            mListener = (OnZhiHuItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnZhiHuItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initViewsAndEvents() {
        ButterKnife.bind(this, getRootView());
        // Set the adapter
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mListAdapter = new ZhiHuAdapter(mList, mListener);
        mRecyclerView.setAdapter(mListAdapter);
    }

    @Override
    protected void initData() {
        getPosts();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //根据 Tag 取消请求
        OkHttpUtils.getInstance().cancelTag(this);
    }

    /**
     * 用途：获取「看知乎」首页文章列表，每次取10篇。
     * 参数：时间戳（可选，留空时取最新10篇，有值时则取此时间戳之前的10篇）
     */
    private void getPosts() {
        L.d("getPosts");
        OkHttpUtils.get(API.GET_POSTS)
        .tag(this)
        .cacheKey(API.GET_POSTS)            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
        .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
        .execute(new AbsCallback<Posts>() {
            @Override
            public Posts parseNetworkResponse(Response response) throws Exception {
                String responseData = response.body().string();
                Posts posts = GsonWrapper.gson.fromJson(responseData, Posts.class);
                if(TextUtils.isEmpty(posts.getError())) {
                    return posts;
                } else {
                    throw new IllegalStateException("错误信息：" + posts.getError());
                }
            }

            @Override
            public void onSuccess(Posts posts, Call call, Response response) {
                if(posts.getPosts() != null) {
                    mList.addAll(posts.getPosts());
                    mListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *  这个接口必须被包含此fragment的activities实现，以允许fragment与activity交互
     */
    public interface OnZhiHuItemClickListener {
        // TODO: Update argument type and name
        void OnZhiHuItemClick(Posts.PostsBean item);
    }
}
