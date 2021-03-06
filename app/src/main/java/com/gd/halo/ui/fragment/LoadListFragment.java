package com.gd.halo.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gd.halo.R;
import com.gd.halo.base.GLazyFragment;
import com.gd.halo.ui.widget.LoadRecyclerView;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnLoadListClickListener}
 * interface.
 */
public abstract class LoadListFragment extends GLazyFragment implements SwipeRefreshLayout.OnRefreshListener, LoadRecyclerView.OnLoadMoreListener {

    // TODO: Customize parameters
    private int mColumnCount = 1;

    SwipeRefreshLayout swipe_refresh_layout;

    LoadRecyclerView recycler_view;
    private RecyclerView.Adapter mAdapter;

    protected OnLoadListClickListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LoadListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initViewsAndEvents() {
        swipe_refresh_layout = findViewById(R.id.swipe_refresh_layout);
        recycler_view = findViewById(R.id.recycler_view);
        swipe_refresh_layout.setOnRefreshListener(this);
        // 顶部刷新的样式
        swipe_refresh_layout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light);
        // Set the adapter
        Context context = recycler_view.getContext();
        if (mColumnCount <= 1) {
            recycler_view.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recycler_view.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        swipe_refresh_layout.setEnabled(canRefresh());
        recycler_view.setCanLoadMore(canLoad());
        recycler_view.setOnLoadMoreListener(this);
        mAdapter = getAdapter();
        recycler_view.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int layoutResID() {
        return R.layout.fragment_loaditem_list;
    }

    protected abstract RecyclerView.Adapter getAdapter();

    protected abstract boolean canRefresh();

    protected abstract boolean canLoad();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoadListClickListener) {
            mListener = (OnLoadListClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLoadListClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {

    }

    /**
     * 刷新成功
     */
    protected void onRefreshFinish(){
        swipe_refresh_layout.setRefreshing(false);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 刷新失败
     */
    protected void onRefreshError(){
        swipe_refresh_layout.setRefreshing(false);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化数据成功
     */
    protected void onInitDataFinish(){
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化数据失败
     */
    protected void onInitDataError(){

    }

    /**
     * 加载更多成功
     */
    protected void onLoadFinish(){
        mAdapter.notifyDataSetChanged();
        recycler_view.loadMoreComplete();
    }

    /**
     * 加载更多失败
     */
    protected void onLoadError(){
        recycler_view.loadMoreError();
    }

    @Override
    public void onLoadMore() {

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
     */
    public interface OnLoadListClickListener {
        void OnLoadListClick(Object item);
    }
}
