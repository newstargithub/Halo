package com.gd.halo.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.gd.halo.bean.LoadState;
import com.gd.halo.ui.adapter.LoadAdapter;

/**
 * Created by zhouxin on 2016/7/12.
 * Description:
 */
public class LoadRecyclerView extends RecyclerView {
    boolean isLoadingData;
    private OnLoadMoreListener loadMoreListener;
    //是否可加载更多
    private boolean canLoadMore = true;
    private LoadAdapter mLoadAdapter;
    private LoadState mLoadState;

    public LoadRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public LoadRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mLoadState = new LoadState();
        mLoadState.setError("加载出错");
        mLoadState.setState(LoadState.STATE_NONE);
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == RecyclerView.SCROLL_STATE_IDLE && loadMoreListener != null && !isLoadingData && canLoadMore) {
            LayoutManager layoutManager = getLayoutManager();
            int lastVisibleItemPosition;
            if (layoutManager instanceof GridLayoutManager) {
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                lastVisibleItemPosition = last(into);
            } else {
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
            if (layoutManager.getChildCount() > 0
                    && lastVisibleItemPosition >= layoutManager.getItemCount() - 1) {
                notifyLoadMore();
            }
        }
    }

    private void notifyLoadMore() {
        if (mLoadAdapter != null) {
            mLoadState.setState(LoadState.STATE_LOAD);
            mLoadAdapter.setLoadState(mLoadState);
        }
        isLoadingData = true;
        if(loadMoreListener != null) {
            loadMoreListener.onLoadMore();
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if(canLoadMore) {
            mLoadAdapter = new LoadAdapter(adapter);
            mLoadAdapter.setLoadState(mLoadState);
            mLoadAdapter.setListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyLoadMore();
                }
            });
            super.setAdapter(mLoadAdapter);
        } else {
            super.setAdapter(adapter);
        }
    }

    //设置是否可加载更多
    public void setCanLoadMore(boolean flag) {
        canLoadMore = flag;
    }

    //设置加载更多监听
    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        loadMoreListener = listener;
    }

    //加载完成
    public void loadMoreComplete() {
        if (mLoadAdapter != null) {
            mLoadState.setState(LoadState.STATE_NONE);
            mLoadAdapter.setLoadState(mLoadState);
        }
        isLoadingData = false;
    }

    //加载出错
    public void loadMoreError() {
        if (mLoadAdapter != null) {
            mLoadState.setState(LoadState.STATE_ERROR);
            mLoadAdapter.setLoadState(mLoadState);
        }
        isLoadingData = false;
    }

    //取到最后的一个节点
    private int last(int[] lastPositions) {
        int last = lastPositions[0];
        for (int value : lastPositions) {
            if (value > last) {
                last = value;
            }
        }
        return last;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
