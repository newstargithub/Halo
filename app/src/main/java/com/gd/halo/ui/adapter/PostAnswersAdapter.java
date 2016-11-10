package com.gd.halo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gd.halo.R;
import com.gd.halo.bean.PostAnswers;
import com.gd.halo.ui.fragment.LoadListFragment;
import com.gd.halo.util.ResUtil;
import com.gd.halo.util.glide.GlideCircleTransform;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostAnswersAdapter extends RecyclerView.Adapter<PostAnswersAdapter.ViewHolder> {

    private final List<PostAnswers.AnswersBean> mValues;
    private final LoadListFragment.OnLoadListClickListener mListener;

    public PostAnswersAdapter(List<PostAnswers.AnswersBean> items, LoadListFragment.OnLoadListClickListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragment_post_answers, parent, false);
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
                    mListener.OnLoadListClick(holder.mItem);
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
        @BindView(R.id.avatar)
        public ImageView avatar;
        @BindView(R.id.author_name)
        public TextView author_name;
        @BindView(R.id.vote)
        public TextView vote;
        @BindView(R.id.title)
        public TextView title;
        @BindView(R.id.summary)
        public TextView summary;
        public PostAnswers.AnswersBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

        public void bindView(PostAnswers.AnswersBean item) {
            mItem = item;
            Context context = avatar.getContext();
            Glide.with(context)
                    .load(mItem.getAvatar())
                    .centerCrop()
                    .transform(new GlideCircleTransform(context))
                    .placeholder(R.drawable.loading_spinner)
                    .crossFade()
                    .into(avatar);
            author_name.setText(ResUtil.getString(R.string.f_author_name_answer, mItem.getAuthorname()));
            vote.setText(mItem.getVote());
            title.setText(mItem.getTitle());
            summary.setText(mItem.getSummary());
        }
    }
}
