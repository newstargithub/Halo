package com.gd.halo.presenter;

import com.gd.halo.bean.Jztk;
import com.gd.halo.model.DataManager;
import com.gd.halo.view.JztkView;
import com.gudeng.library.base.BasePresenter;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhouxin on 2016/11/11.
 * Description:
 */
public class JztkPresenter extends BasePresenter<JztkView>{
    private final DataManager mDataManager;

    public JztkPresenter(){
        mDataManager = DataManager.getInstance();
    }

    public void getRandJztk(String subject, String model){
        final JztkView view = getView();
        view.showLoading(null);
        mDataManager.getRandJztk(subject, model)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Jztk>>() {
                    @Override
                    public void onCompleted() {
                        if (isViewAttach()) {
                            view.hideLoading();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttach()) {
                            view.hideLoading();
                            view.showError(null);
                        }
                    }

                    @Override
                    public void onNext(List<Jztk> list) {
                        if (isViewAttach()) {
                            view.showData(list);
                        }
                    }
                });
    }
}
