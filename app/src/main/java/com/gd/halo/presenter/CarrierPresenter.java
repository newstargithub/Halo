package com.gd.halo.presenter;

import com.gd.halo.bean.Carrier;
import com.gd.halo.model.DataManager;
import com.gd.halo.view.CarrierListView;
import com.gudeng.library.base.BasePresenter;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhouxin on 2016/11/9.
 * Description: 快递在线下单
 * MvpView接口: 提供一些列简洁的方法函数，比如showError( )或者showProgressIndicator( )
 * mMvpView是与Presenter一起协助的View组件。通常情况下是一个Activity，Fragment或者ViewGroup的实例。
 *
 */
public class CarrierPresenter extends BasePresenter<CarrierListView>{
    private CarrierListView mMvpView;
    private DataManager mDataManager;
    private Subscription mSubscription;

    public CarrierPresenter(CarrierListView view){
        mDataManager = DataManager.getInstance();
        mMvpView = view;
    }

    public void getCarriers() {
        mMvpView.showProgressIndicator(true);
        mSubscription = mDataManager.getCarriers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Carrier>>() {
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
                    public void onNext(List<Carrier> list) {
                        mMvpView.showData(list);
                    }
                });
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }
}
