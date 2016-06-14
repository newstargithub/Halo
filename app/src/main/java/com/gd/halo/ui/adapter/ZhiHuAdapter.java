package com.gd.halo.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gd.halo.R;
import com.gd.halo.bean.Posts;
import com.gd.halo.ui.fragment.NewsFragment.OnListFragmentInteractionListener;
import com.gd.halo.ui.fragment.ZhiHuFragment;
import com.gd.halo.ui.fragment.dummy.DummyContent.DummyItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * 知乎列表.
 */
public class ZhiHuAdapter extends RecyclerView.Adapter<ZhiHuAdapter.ViewHolder> {

    private final List<Posts.PostsBean> mValues;
    private final ZhiHuFragment.OnZhiHuItemClickListener mListener;

    public ZhiHuAdapter(List<Posts.PostsBean> items, ZhiHuFragment.OnZhiHuItemClickListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragment_zhihu, parent, false);
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
                    mListener.OnZhiHuItemClick(holder.mItem);
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
        @Bind(R.id.title)
        public TextView title;
        @Bind(R.id.content)
        public TextView content;
        @Bind(R.id.count)
        public TextView count;
        public Posts.PostsBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

        public void bindView(Posts.PostsBean item) {
            mItem = item;
            title.setText(mItem.getName());
            content.setText(mItem.getExcerpt());
            count.setText(mItem.getCount());
        }
    }
}
