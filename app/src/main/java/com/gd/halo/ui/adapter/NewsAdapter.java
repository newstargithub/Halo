package com.gd.halo.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gd.halo.R;
import com.gd.halo.bean.NewsBean;
import com.gd.halo.ui.fragment.NewsFragment.OnNewsItemClickListener;
import com.gd.halo.ui.fragment.dummy.DummyContent.DummyItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnNewsItemClickListener}.
 * 新闻列表.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final List<NewsBean> mValues;
    private final OnNewsItemClickListener mListener;

    public NewsAdapter(List<NewsBean> items, OnNewsItemClickListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragment_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bindView(mValues.get(position));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.OnNewsItemClick(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.title)
        public TextView mTitleView;
        @BindView(R.id.content)
        public TextView mContentView;
        @BindView(R.id.time)
        public TextView mTimeView;
        public NewsBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        public void bindView(NewsBean item) {
            mItem = item;
            mTitleView.setText(mItem.getTitle());
            mContentView.setText(mItem.getDescription());
            mTimeView.setText(mItem.getPubTime());
        }
    }
}
