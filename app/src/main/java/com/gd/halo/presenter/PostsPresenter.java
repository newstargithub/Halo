package com.gd.halo.presenter;

import com.gd.halo.bean.Post;
import com.gd.halo.model.DataManager;
import com.gd.halo.view.PostsView;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhouxin on 2016/11/7.
 * Description: 博客文章
 * MvpView接口: 提供一些列简洁的方法函数，比如showError( )或者showProgressIndicator( )
 * mMvpView是与Presenter一起协助的View组件。通常情况下是一个Activity，Fragment或者ViewGroup的实例。
 *
 */
public class PostsPresenter {
    private PostsView mMvpView;
    private DataManager mDataManager;
    private Subscription mSubscription;

    public void loadTodayPosts() {
        mMvpView.showProgressIndicator(true);
        mSubscription = mDataManager.loadTodayPosts().toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Post>>() {
                    @Override
                    public void onCompleted() {
                        mMvpView.showProgressIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mMvpView.showProgressIndicator(false);
                        mMvpView.showError();
                    }

                    @Override
                    public void onNext(List<Post> postsList) {
                        mMvpView.showPosts(postsList);
                    }
                });
    }
}
