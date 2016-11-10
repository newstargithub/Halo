package com.gd.halo.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gd.halo.R;
import com.gd.halo.bean.LoadState;
import com.pnikosis.materialishprogress.ProgressWheel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhouxin on 2016/7/12.
 * Description: 包装加载更多的视图显示
 */
public class LoadAdapter extends RecyclerView.Adapter {

    private static final int ITEM_LOAD = Integer.MAX_VALUE;
    RecyclerView.Adapter mAdapter;
    private LoadState mLoadState;
    private View.OnClickListener mListener;

    public LoadAdapter(RecyclerView.Adapter adapter) {
        if (mAdapter != null && mDataObserver != null) {
            mAdapter.unregisterAdapterDataObserver(mDataObserver);
        }
        mAdapter = adapter;
        mAdapter.registerAdapterDataObserver(mDataObserver);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM_LOAD) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_load, parent, false);
            return new ViewHolder(view);
        } else {
            return mAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_LOAD) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.bindView(mLoadState);
        } else {
            mAdapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position < mAdapter.getItemCount()) {
            return mAdapter.getItemViewType(position);
        } else {
            return ITEM_LOAD;
        }
    }

    @Override
    public int getItemCount() {
        if(mAdapter.getItemCount() == 0) {
            return 0;
        } else {
            return mAdapter.getItemCount() + 1;
        }
    }

    public void setListener(View.OnClickListener mListener) {
        this.mListener = mListener;
    }

    public void setLoadState(LoadState mLoadState) {
        this.mLoadState = mLoadState;
        if(getItemCount() > 0) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.tv_text)
        public TextView tv_text;
        @BindView(R.id.progress_wheel)
        public ProgressWheel progress_wheel;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

        public void bindView(LoadState loadState) {
            if(loadState.getState() == LoadState.STATE_NONE) {
                mView.setVisibility(View.GONE);
            } else if(loadState.getState() == LoadState.STATE_LOAD) {
                mView.setVisibility(View.VISIBLE);
                progress_wheel.setVisibility(View.VISIBLE);
                progress_wheel.spin();
                tv_text.setVisibility(View.INVISIBLE);
                mView.setOnClickListener(null);
            } else {
                mView.setVisibility(View.VISIBLE);
                progress_wheel.setVisibility(View.INVISIBLE);
                progress_wheel.stopSpinning();
                tv_text.setVisibility(View.VISIBLE);
                tv_text.setText(loadState.getError());
                mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mListener) {
                            // Notify the active callbacks interface (the activity, if the
                            // fragment is attached to one) that an item has been selected.
                            mListener.onClick(v);
                        }
                    }
                });
            }
        }
    }

    private final RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            notifyItemMoved(fromPosition, toPosition);
        }
    };
}
