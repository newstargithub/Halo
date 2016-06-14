package com.gd.halo.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gd.halo.R;
import com.gd.halo.base.LazyFragment;
import com.gd.halo.bean.NewsBean;
import com.gd.halo.net.DialogCallback;
import com.gd.halo.support.xml.SAXNewsParse;
import com.gd.halo.ui.adapter.NewsAdapter;
import com.gd.halo.util.L;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A fragment representing a list of Items.
 * <p />
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class NewsFragment extends LazyFragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;
    private String mTitle;
    private String mUrl;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_TITLE = "title";
    private static final String ARG_URL = "url";

    private OnListFragmentInteractionListener mListener;
    private NewsAdapter mNewsAdapter;
    private List<NewsBean> mList = new ArrayList<>();
    @Bind(R.id.list)
    RecyclerView mRecyclerView;

    // TODO: Customize parameter initialization
    public static NewsFragment newInstance(int columnCount, String title, String url) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NewsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mTitle = getArguments().getString(ARG_TITLE);
            mUrl = getArguments().getString(ARG_URL);
            TAG = TAG + mTitle;
        }
    }

    @Override
    protected int layoutResID() {
        return R.layout.fragment_news_list;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
        if (mColumnCount <= 1) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, mColumnCount));
        }
        mNewsAdapter = new NewsAdapter(mList, mListener);
        mRecyclerView.setAdapter(mNewsAdapter);
    }

    @Override
    protected void initData() {
        getNews();
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

    private void getNews() {
        L.d("getNews:" + mTitle);
        OkHttpUtils.get(mUrl)
        .tag(this)
        .cacheKey(mUrl)            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
        .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
        .execute(new DialogCallback<List<NewsBean>>(getActivity()){
            @Override
            public List<NewsBean> parseNetworkResponse(Response response) throws Exception {
                return SAXNewsParse.parse(response.body().byteStream());
            }

            @Override
            public void onResponse(boolean isFromCache, List<NewsBean> newsBeen, Request request, @Nullable Response response) {
                if(newsBeen != null) {
                    mList.addAll(newsBeen);
                    mNewsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
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
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(NewsBean item);
    }
}
